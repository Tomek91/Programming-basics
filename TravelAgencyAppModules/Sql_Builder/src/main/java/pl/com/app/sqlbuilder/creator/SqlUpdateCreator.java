package pl.com.app.sqlbuilder.creator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlUpdateCreator {

    private List<String> commands = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();

    private SqlUpdateCreator(SqlUpdateBuilder builder) {
        this.commands = builder.commands;
        this.conditions = builder.conditions;
    }

    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        command.append("update ");
        command.append(commands.get(0));
        command.append(" set ");
        command.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        if (!conditions.isEmpty()){
            command.append(" where ");
            command.append(conditions.stream().collect(Collectors.joining(", ")));
        }
        command.append(";");
        return command.toString();
    }

    public static class SqlUpdateBuilder {
        private List<String> commands = new ArrayList<>();
        private List<String> conditions = new ArrayList<>();
        private boolean isTableNameAdded = false;

        public SqlUpdateBuilder tableName(String tableName) {
            if (!isTableNameAdded) {
                isTableNameAdded = true;
                commands.add(0, tableName);
            }
            return this;
        }

        public SqlUpdateBuilder addCondition(String columnName, Object value) {
            conditions.add(columnName + " = " + value);
            return this;
        }

        public SqlUpdateBuilder addSetClause(String columnName, Object value) {
            StringBuilder sb = new StringBuilder(String.valueOf(value));
            if (value instanceof String || value instanceof Timestamp || value instanceof Date){
                sb.insert(0, "'");
                sb.append("'");
            }
            commands.add(columnName + " = " + sb.toString());
            return this;
        }

        public String build() {
            return new SqlUpdateCreator(this).toString();
        }
    }
}
