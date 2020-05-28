-- 将此文件放在 Resource 目录下，在应用启动时会执行此文件中的语句
-- 约定文件名默认为 schema.sql
-- 约定此文件中存放 ddl 语句
-- 如果要自定义文件名和位置，spring.datasource.schema=schema.sql,schema2.sql
-- Spring Boot2.x 必须添加 initialization-mode 配置才会执行，默认为 embedded 嵌入式数据库（H2这种），如在 mysql 下需设置为 always
-- 如果sql脚本执行的数据库用户名和密码不相同，需要设置单独的属性
CREATE TABLE foo
(
    id  int identity,
    bar varchar(64)
);
