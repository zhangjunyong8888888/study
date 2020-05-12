package com.zjy.study.modules.java8.stream.sqlSelect;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLOrderingSpecification;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：静态数据SQL执行器
 *
 * 支持：
 *      单表查询
 *      SELECT 指定字段
 *      WHERE 等于、大于、小于条件判断
 *      ORDER BY 指定字段正序、倒序排序
 *      LIMIT 分页
 */
public class SqlExecutor {

    /**
     * 集合数据SQL执行方法
     *
     * 利用Stream流对集合数据实现SQL操作
     * 支持：
     *      单表查询
     *      SELECT 指定字段
     *      WHERE 等于、大于、小于条件判断
     *      ORDER BY 指定字段正序、倒序排序
     *      LIMIT 分页
     *
     * @param clazz - 操作对象类型
     * @param list - 集合数据
     * @param sql - SQL语句
     * @param <T> - 泛型
     * @return - 结果对象
     */
    public static <T> Object execute(Class<T> clazz, List<T> list, String sql) {
        /**
         * 根据集合数据生成Stream流
         */
        Stream<T> stream = list.stream();

        /**
         * 解析SQL
         */
        SQLSelectQueryBlock sqlSelectQueryBlock = parseSql(sql);

        /**
         * 使用filter处理where
         */
        SQLExpr where = sqlSelectQueryBlock.getWhere();
        if (where != null) {
            stream = stream.filter(t -> parseWhere(t, ((SQLBinaryOpExpr) where)));
        }


        /**
         * 使用sorted处理order by
         */
        SQLOrderBy order = sqlSelectQueryBlock.getOrderBy();
        if (order != null) {
            stream = stream.sorted(parseOrder(clazz, order));
        }


        /**
         * 使用skip、limit处理limit
         */
        SQLLimit limit = sqlSelectQueryBlock.getLimit();
        if (limit != null) {
            Integer offset = Integer.parseInt(SQLUtils.toMySqlString(limit.getOffset()));
            Integer rowCount = Integer.parseInt(SQLUtils.toMySqlString(limit.getRowCount()));

            stream = stream.skip((offset - 1) * rowCount)
                    .limit(rowCount);
        }

        /**
         * 使用map处理select
         */
        List<SQLSelectItem> selectList = sqlSelectQueryBlock.getSelectList();
        if (CollectionUtils.isNotEmpty(selectList)) {
            stream = stream.map(t -> {

                List<String> selectItems = Lists.newArrayList();
                selectList.forEach(sqlSelectItem -> {

                    // 收集需要显示的字段
                    String selectItem = SQLUtils.toMySqlString(sqlSelectItem);
                    selectItem = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, selectItem);
                    selectItems.add(selectItem);
                });

                // 利用FastJson的序列化和反序列号实现字段拷贝，性能上会有问题
                SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(selectItems.toArray(new String[]{}));
                T target = JSON.parseObject(JSON.toJSONString(t, simplePropertyPreFilter), clazz);

                return target;
            });
        }

        /**
         * 使用collect收集结果
         */
        Object result = stream.collect(Collectors.toList());

        // 输出结果
        return result;
    }

    /**
     * 利用Druid包中SQL解析器解析SQL
     * @param sql - 原始SQL语句
     * @return - 解析后SQL对象
     */
    private static SQLSelectQueryBlock parseSql(String sql) {
        // 新建 Mysql Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST
        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) parser.parseStatement();

        // 获取查询实体信息
        SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectStatement.getSelect().getQuery();

        return sqlSelectQueryBlock;
    }

    /**
     * 处理order
     * @param clazz
     * @param order
     * @param <T>
     * @return
     */
    private static <T> Comparator parseOrder(Class<T> clazz, SQLOrderBy order) {
        Comparator<T> comparator = null;

        for (SQLSelectOrderByItem orderByItem : order.getItems()) {
            final String orderItem = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, SQLUtils.toMySqlString(orderByItem.getExpr()));

            // 构造Comparator
            Comparator<T> orderByItemComparator;
            try {
                orderByItemComparator = new Comparator<T>() {
                    PropertyDescriptor descriptor = new PropertyDescriptor(orderItem, clazz);

                    @Override
                    public int compare(T t1, T t2) {
                        Integer result;
                        try {
                            Object object1 = descriptor.getReadMethod().invoke(t1);
                            Object object2 = descriptor.getReadMethod().invoke(t2);

                            if (object1 instanceof Integer) {
                                result = ((Integer) object1).compareTo((Integer) object2);
                            } else if (object1 instanceof Date) {
                                result = ((Date) object1).compareTo((Date) object2);
                            } else if (object1 instanceof Double) {
                                result = ((Double) object1).compareTo((Double) object2);
                            } else if (object1 instanceof String) {
                                result = ((String) object1).compareTo((String) object2);
                            } else if (object1 instanceof Long) {
                                result = ((Long) object1).compareTo((Long) object2);
                            } else {
                                throw new RuntimeException("比较类型暂不支持！");
                            }
                        } catch (Exception e) {throw new RuntimeException(e);}

                        return result;
                    }
                };
            } catch (Exception e) {throw new RuntimeException(e);}

            // 如果是DESC，排序反转
            if (SQLOrderingSpecification.DESC.equals(orderByItem.getType())) {
                orderByItemComparator = orderByItemComparator.reversed();
            }

            // 为空赋值，非空追加
            if (comparator == null) {
                comparator = orderByItemComparator;
            } else {
                comparator = comparator.thenComparing(orderByItemComparator);
            }
        }

        return comparator;
    }

    /**
     * 处理where
     * @param t
     * @param where
     * @param <T>
     * @return
     */
    private static <T> boolean parseWhere(T t, SQLBinaryOpExpr where) {
        // 获取where条件
        SQLExpr left = where.getLeft();
        SQLExpr right = where.getRight();

        // 获取操作类型
        SQLBinaryOperator operator = where.getOperator();

        if (SQLBinaryOperator.BooleanAnd.equals(operator)) {
            // 如果是AND，分别计算左右两边求与
            return parseWhere(t, ((SQLBinaryOpExpr) left)) && parseWhere(t, ((SQLBinaryOpExpr) right));

        } else if (SQLBinaryOperator.BooleanOr.equals(operator)) {
            // 如果是OR，分别计算左右两边求或
            return parseWhere(t, ((SQLBinaryOpExpr) left)) || parseWhere(t, ((SQLBinaryOpExpr) right));

        } else if (SQLBinaryOperator.BooleanXor.equals(operator)) {
            // 暂不支持
            throw new RuntimeException("比较组合暂不支持！");

        } else {
            // 如果不是逻辑关系，直接求结果
            String whereItem = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, SQLUtils.toMySqlString(left));
            String whereValue = SQLUtils.toMySqlString(right).replaceAll("'", "");

            PropertyDescriptor descriptor = null;
            try {
                descriptor = new PropertyDescriptor(whereItem, t.getClass());
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }

            if (SQLBinaryOperator.Equality.equals(operator)) {
                // 相等判定
                try {
                    Object object = descriptor.getReadMethod().invoke(t);

                    if (object instanceof Integer) {
                        return object.equals(Integer.parseInt(whereValue));
                    } else if (object instanceof Double) {
                        return object.equals(Double.parseDouble(whereValue));
                    }else if (object instanceof String) {
                        return object.equals(whereValue);
                    } else {
                        throw new RuntimeException("比较类型暂不支持！");
                    }
                } catch (Exception e) {throw new RuntimeException(e);}

            } else if (SQLBinaryOperator.GreaterThan.equals(operator)) {
                // 大于判定
                try {
                    Object object = descriptor.getReadMethod().invoke(t);

                    if (object instanceof Integer) {
                        return ((Integer) object) > (Integer.parseInt(whereValue));
                    } else if (object instanceof Double) {
                        return ((Double) object) > (Double.parseDouble(whereValue));
                    } else if (object instanceof Date) {
                        return ((Date) object).compareTo(DateUtils.parseDate(whereValue, "yyyy-MM-dd HH:mm:ss")) > 0;
                    } else {
                        throw new RuntimeException("比较类型暂不支持！");
                    }

                } catch (Exception e) {throw new RuntimeException(e);}

            } else if (SQLBinaryOperator.LessThan.equals(operator)) {
                // 小于判定
                try {
                    Object object = descriptor.getReadMethod().invoke(t);

                    if (object instanceof Integer) {
                        return ((Integer) object) < (Integer.parseInt(whereValue));
                    } else if (object instanceof Double) {
                        return ((Double) object) < (Double.parseDouble(whereValue));
                    } else if (object instanceof Date) {
                        return ((Date) object).compareTo(DateUtils.parseDate(whereValue, "yyyy-MM-dd HH:mm:ss")) < 0;
                    } else {
                        throw new RuntimeException("比较类型暂不支持！");
                    }
                } catch (Exception e) {throw new RuntimeException(e);}

            } else {
                // 其他判定暂不支持
                throw new RuntimeException("比较条件暂不支持！");
            }
        }
    }
}
