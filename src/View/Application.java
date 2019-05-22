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
    private JTextArea FirstList;
    private JTextArea FollowLIst;
    private JButton AddRule;
    private JButton AddFollow;
    private JLabel Status;

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
                    FollowLIst.setText(ConverterUtil.INSTANCE.toShowRule(follows));
                }
            }
        });
        Check.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                HashMap<String, Set<String>> _rules = ConverterUtil.INSTANCE.toRules(rules);
                HashMap<String, Set<String>> _follows = ConverterUtil.INSTANCE.toFollow(follows);
                HashSet<String> nonTerminal = GeneratorUtil.INSTANCE.generateNonTerminal(_rules);
                HashMap<String, HashSet<String>> firsts = GeneratorUtil.INSTANCE.generateFirst(_rules, nonTerminal);

                FirstList.setText(ConverterUtil.INSTANCE.toShowFirsts(firsts));
                MainHandler.INSTANCE.run(_rules, nonTerminal, _follows, firsts);
            }
        });

    }


    public JPanel getPanel() {
        return panel;
    }


}
