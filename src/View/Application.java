package View;

import Handlers.MainHandler;
import Utils.ConverterUtil;
import Utils.GeneratorUtil;
import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.*;

public class Application {

    private JPanel panel;
    private JTable LL1Table;
    private JTextField InputRules;
    private JTextField Checker;
    private JButton Check;
    private JTextArea Rules;
    private JButton AddRule;
    private JLabel Status;
    private JScrollPane Table;
    private JTable FirstTable;
    private JTable FollowTable;
    private JButton generateButton;
    private JButton addDef;

    private List<String> rules;
    private HashMap<String, Set<Pair<String, Integer>>> _rules;
    private HashMap<Pair<String, String>, Integer> table;

    public Application() {

        rules = new ArrayList<>();
        _rules = new HashMap<>();
        table = new HashMap<>();



        AddRule.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rule = InputRules.getText().trim();
                if (!rule.isEmpty()) {
                    rules.add(rule);
                    InputRules.setText(null);
                    Rules.setText(ConverterUtil.INSTANCE.toShowRule(rules));
                }
            }
        });

        addDef.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rules.isEmpty()) {

                    rules.add("Program → Statement");
                    rules.add("Statement → if Expression then Block | while Expression do Block | Expression ;");
                    rules.add("Expression → Term => identifier | isZero? Term | not Expression | ++ identifier | -- identifier");
                    rules.add("Term → identifier | constant");
                    rules.add("Block → Statement | { Statements }");
                    rules.add("Statements → Statement Statements | ε");

                    Rules.setText(ConverterUtil.INSTANCE.toShowRule(rules));
                }
            }
        });

        generateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rules.isEmpty()) {
                    Status.setText("Generating Data...");
                    _rules.putAll(ConverterUtil.INSTANCE.toRules(rules));
                    HashSet<String> nonTerminal = GeneratorUtil.INSTANCE.generateNonTerminal(_rules);
                    HashSet<String> terminal = GeneratorUtil.INSTANCE.generateTerminal(_rules);
                    HashMap<String, HashSet<String>> firsts = GeneratorUtil.INSTANCE.generateFirst(_rules, nonTerminal);
                    HashMap<String, HashSet<String>> follows = GeneratorUtil.INSTANCE.generateFollow(_rules, nonTerminal, ConverterUtil.INSTANCE.getStartRule(rules), firsts);

                    MainHandler.INSTANCE.generateFirstTable(firsts
                            , model -> FirstTable.setModel(model));

                    MainHandler.INSTANCE.generateFollowTable(follows,
                            model -> FollowTable.setModel(model));

                    MainHandler.INSTANCE.run(_rules, nonTerminal, terminal, follows, firsts
                            , (tableModel, _LL1Table) -> {
                                Status.setText("Generating Done!");
                                LL1Table.setModel(tableModel);
                                table.putAll(_LL1Table);

                            });
                } else {
                    Status.setForeground(Color.RED);
                    Status.setText("Enter Rules First!!");
                }
            }
        });

        Check.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!table.isEmpty()) {
                    String data = Checker.getText();

                    if (data.isEmpty() || data.isBlank()) {
                        Status.setForeground(Color.RED);
                        Status.setText("Enter Valid Data!!");
                    } else {
                        MainHandler.INSTANCE.check(data
                                , ConverterUtil.INSTANCE.getStartRule(rules)
                                , table
                                , _rules
                                , isAccepted -> {
                                    if (isAccepted) {
                                        Status.setText("Accepted");
                                        Status.setForeground(Color.GREEN);
                                    } else {
                                        Status.setText("Rejected");
                                        Status.setForeground(Color.RED);
                                    }
                                });
                    }
                } else {
                    Status.setForeground(Color.RED);
                    Status.setText("Generate Table First!!");
                }

            }
        });

    }


    public JPanel getPanel() {
        return panel;
    }


}
