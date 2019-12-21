package pl.com.app.sqlbuilder.creator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlInsertCreator {
    private List<String> commands = new ArrayList<>();
    private List<String> values = new ArrayList<>();

    private SqlInsertCreator(SqlInsertBuilder builder) {
        this.commands = builder.commands;
        this.values = builder.values;
    }

    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        command.append("insert into ");
        command.append(commands.get(0));
        command.append(" (");
        command.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        command.append(" ) values (");
        command.append(values.stream().collect(Collectors.joining(", ")));
        command.append(");");
        return command.toString();
    }

    public static class SqlInsertBuilder {
        private List<String> commands = new ArrayList<>();
        private List<String> values = new ArrayList<>();
        private boolean isTableNameAdded = false;

        public SqlInsertBuilder tableName(String tableName) {
            if (!isTableNameAdded) {
                isTableNameAdded = true;
                commands.add(0, tableName);
            }
            return this;
        }

        public SqlInsertBuilder addCondition(String columnName, Object value) {
            commands.add(columnName);
            StringBuilder sb = new StringBuilder(String.valueOf(value));
            if (value instanceof String || value instanceof Timestamp || value instanceof Date){
                sb.insert(0, "'");
                sb.append("'");
            }
            values.add(sb.toString());
            return this;
        }

        public String build() {
            return new SqlInsertCreator(this).toString();
        }
    }
}
