package galaxis.lee.models;

import java.util.Objects;

public class DatabaseConfig {

    private String dbType;    //数据库类型

    private String host;    //主机名或IP地址

    private String port;    //端口号

    private String username;    //用户名

    private String password;    //密码

    private String schema;        //Schema/数据库

    private String encoding;    //编码

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseConfig that = (DatabaseConfig) o;
        return Objects.equals(dbType, that.dbType) &&
                Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(schema, that.schema) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(encoding, that.encoding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbType, host, port, schema, username, password, encoding);
    }

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                ", dbType='" + dbType + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", schema='" + schema + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", encoding='" + encoding + '\'' +
                '}';
    }
}
