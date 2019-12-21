package pl.com.app.sqlbuilder.creator;

import pl.com.app.sqlbuilder.types.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SqlTableCreator {
    private List<String> commands = new ArrayList<>();
    private List<String> foreignKeys = new ArrayList<>();

    private SqlTableCreator(SqlTableCreatorBuilder builder) {
        this.commands = builder.commands;
        this.foreignKeys = builder.foreignKeys;
    }

    @Override
    public String toString() {
        StringBuilder command = new StringBuilder();
        command.append("create table if not exists ");
        command.append(commands.get(0));
        command.append("( ");
        command.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        if (!foreignKeys.isEmpty()){
            command.append(", ");
            command.append(foreignKeys.stream().collect(Collectors.joining(", ")));
        }
        command.append(");");
        return command.toString();
    }

    public static class SqlTableCreatorBuilder {
        private List<String> commands = new ArrayList<>();
        private List<String> foreignKeys = new ArrayList<>();
        private boolean isTableNameAdded = false;
        private boolean isPrimaryKeyAdded = false;

        public SqlTableCreatorBuilder tableName(String tableName) {
            if (!isTableNameAdded) {
                isTableNameAdded = true;
                commands.add(0, tableName);
            }
            return this;
        }

        public SqlTableCreatorBuilder addPrimaryKey(String sqlColumn) {
            if (!isPrimaryKeyAdded) {
                isPrimaryKeyAdded = true;
                commands.add(1, sqlColumn + " integer primary key autoincrement");
            }
            return this;
        }

        public SqlTableCreatorBuilder addColumn(String columnName, Types type) {
            commands.add(String.join(" ", columnName, type.getCode()));
            return this;
        }

        public SqlTableCreatorBuilder addForeignKey(String columnName, Types type, String foreignTable, String foreignColumn) {
            foreignKeys.add("FOREIGN KEY(" + columnName + ") REFERENCES " + foreignTable + "(" + foreignColumn + ")");
            return addColumn(columnName, type);
        }

        public SqlTableCreatorBuilder isUnique() {
            addClauseToColumn(" unique");
            return this;
        }

        public SqlTableCreatorBuilder isNotNull() {
            addClauseToColumn(" not null");
            return this;
        }

        public SqlTableCreatorBuilder defaultValue(Object value) {
            addClauseToColumn(" default " + value);
            return this;
        }

        public SqlTableCreatorBuilder maxSize(Integer maxSize) {
            addClauseToColumn("(" + maxSize + ")");
            return this;
        }

        private void addClauseToColumn(final String clause){
            commands.set(commands.size() - 1, commands.get(commands.size() - 1) + clause);
        }

        public String build() {
            return new SqlTableCreator(this).toString();
        }
    }
}
