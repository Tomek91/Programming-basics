package pl.com.app.sqlbuilder.creator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlDeleteCreator {
    private List<String> commands = new ArrayList<>();

    private SqlDeleteCreator(SqlDeleteBuilder builder) {
        this.commands = builder.commands;
    }

    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        command.append("delete from ");
        command.append(commands.get(0));
        if (commands.size() > 1){
            command.append(" where ");
            command.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        }
        command.append(";");
        return command.toString();
    }

    public static class SqlDeleteBuilder {
        private List<String> commands = new ArrayList<>();
        private boolean isTableNameAdded = false;

        public SqlDeleteBuilder tableName(String tableName) {
            if (!isTableNameAdded) {
                isTableNameAdded = true;
                commands.add(0, tableName);
            }
            return this;
        }

        public SqlDeleteBuilder addCondition(String columnName, Long columnId) {
            commands.add(columnName + " = " + columnId);
            return this;
        }

        public SqlDeleteBuilder addCondition(String columnName, Object value) {
            StringBuilder sb = new StringBuilder(String.valueOf(value));
            if (value instanceof String || value instanceof LocalDate){
                sb.insert(0, "'");
                sb.append("'");
            }
            commands.add(String.join("=", columnName, sb.toString()));
            return this;
        }

        public String build() {
            return new SqlDeleteCreator(this).toString();
        }
    }
}
