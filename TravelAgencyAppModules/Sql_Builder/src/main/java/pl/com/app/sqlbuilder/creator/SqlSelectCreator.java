package pl.com.app.sqlbuilder.creator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlSelectCreator {
    private List<String> commands = new ArrayList<>();

    private SqlSelectCreator(SqlSelectBuilder builder) {
        this.commands = builder.commands;
    }

    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        command.append("select * from ");
        command.append(commands.get(0));
        if (commands.size() > 1){
            command.append(" where ");
            command.append(commands.stream().skip(1).collect(Collectors.joining("AND ")));
        }
        command.append(";");
        return command.toString();
    }

    public static class SqlSelectBuilder {
        private List<String> commands = new ArrayList<>();
        private boolean isTableNameAdded = false;

        public SqlSelectBuilder tableName(String tableName) {
            if (!isTableNameAdded) {
                isTableNameAdded = true;
                commands.add(0, tableName);
            }
            return this;
        }

        public SqlSelectBuilder addCondition(String columnName, Object value) {
            StringBuilder sb = new StringBuilder(String.valueOf(value));
            if (value instanceof String || value instanceof LocalDate){
                sb.insert(0, "'");
                sb.append("'");
            }
            commands.add(String.join("=", columnName, sb.toString()));
            return this;
        }

        public String build() {
            return new SqlSelectCreator(this).toString();
        }
    }

}
