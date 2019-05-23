package View;

import Handlers.MainHandler;
import Utils.ConverterUtil;
import Utils.GeneratorUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class Application {

    private JPanel panel;
    private JTable LL1Table;
    private JTextField FollowSet;
    private JTextField InputRules;
    private JTextField Checker;
    private JButton Check;
    private JTextArea Rules;
    private JButton AddRule;
    private JButton AddFollow;
    private JLabel Status;
    private JScrollPane Table;
    private JTable FirstTable;
    private JTable FollowTable;

    private List<String> rules;
    private List<String> follows;


    public Application() {

        rules = new ArrayList<>();
        follows = new ArrayList<>();

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
        AddFollow.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String follow = FollowSet.getText().trim();
                if (!follow.isEmpty()) {
                    follows.add(follow);
                    FollowSet.setText(null);
                }
            }
        });
        Check.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Status.setText("Generating Data...");
                HashMap<String, Set<String>> _rules = ConverterUtil.INSTANCE.toRules(rules);
                HashMap<String, Set<String>> _follows = ConverterUtil.INSTANCE.toFollow(follows);
                HashSet<String> nonTerminal = GeneratorUtil.INSTANCE.generateNonTerminal(_rules);
                HashSet<String> terminal = GeneratorUtil.INSTANCE.generateTerminal(_rules);
                HashMap<String, HashSet<String>> firsts = GeneratorUtil.INSTANCE.generateFirst(_rules, nonTerminal);


                MainHandler.INSTANCE.generateFirstTable(firsts
                        , model -> FirstTable.setModel(model));

                MainHandler.INSTANCE.generateFollowTable(_follows,
                        model -> FollowTable.setModel(model));

                MainHandler.INSTANCE.run(_rules, nonTerminal, terminal, _follows, firsts
                        , table -> {
                            Status.setText("Generating Done!Waiting For Input...");

                        });

            }
        });

    }


    public JPanel getPanel() {
        return panel;
    }


}
