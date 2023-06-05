import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

// Program for print data in JSON format.
public class GroceryList implements ActionListener {

    static ArrayList <String> groceryList = new ArrayList<String>();

    private int WIDTH=800;
    private int HEIGHT=600;

    private JFrame mainFrame;

    private JLabel guideLabel;
//    private JLabel headerLabel;
//    private JLabel urlLabel;
//    private JLabel searchLabel;
    private static JTextArea outputTextArea;
//    private JPanel barPanel;
    private JPanel buttonPanel;
    private JPanel outputPanel;
    private JPanel outputPanelIn;
    private JPanel textPanel;
    private JPanel textPanelIn;
//    private JMenuBar menuBar;
//    private JMenu file, edit, help;
//    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea inputTextArea;
    private static JTextArea processTextArea;
    private JScrollPane scrollPane;
    private static String inputText;
    private String processText;
    private String outputText;

    static double parseConfidence = 0;
    static String term = null;

    static double actionConfidence = 0;
    static String action = null;

    public GroceryList(){
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Grocery List");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2, 1));

//        cut = new JMenuItem("cut");
//        copy = new JMenuItem("copy");
//        paste = new JMenuItem("paste");
//        selectAll = new JMenuItem("selectAll");
//        cut.addActionListener(this);
//        copy.addActionListener(this);
//        paste.addActionListener(this);
//        selectAll.addActionListener(this);

//        menuBar = new JMenuBar();
//        file = new JMenu("File");
//        edit = new JMenu("Edit");
//        help = new JMenu("Help");
//        edit.add(cut);
//        edit.add(copy);
//        edit.add(paste);
//        edit.add(selectAll);
//        menuBar.add(file);
//        menuBar.add(edit);
//        menuBar.add(help);

        inputTextArea = new JTextArea();
        inputTextArea.setBorder(BorderFactory.createTitledBorder("Enter Request:"));
        //textAreaURL.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        processTextArea = new JTextArea();
        processTextArea.setBorder(BorderFactory.createTitledBorder("Process Details:"));
        textPanel = new JPanel(new BorderLayout());
        textPanelIn = new JPanel(new GridLayout(1,2));
        textPanelIn.add(inputTextArea);
        textPanelIn.add(processTextArea);
        buttonPanel = new JPanel(new FlowLayout());
        textPanel.add(textPanelIn, BorderLayout.CENTER);
        textPanel.add(buttonPanel,BorderLayout.SOUTH);

//        headerLabel = new JLabel("", JLabel.CENTER);
        guideLabel = new JLabel("<html><div style='text-align: center;'>This is a program that uses a Natural Language Processing API to process requests.<br>You can add, remove, and order items from the grocery list.</html>", JLabel.CENTER);
//        searchLabel = new JLabel("", JLabel.CENTER);
        outputTextArea = new JTextArea();
        scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Grocery List:"));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //frame.getContentPane().add(scrollableTextArea);

        outputPanelIn = new JPanel(new GridLayout(1,2));
        outputPanelIn.add(guideLabel);
//        outputPanelIn.add(urlLabel);
//        outputPanelIn.add(searchLabel);
        outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputPanelIn, BorderLayout.NORTH);
        outputPanel.add(scrollPane,BorderLayout.CENTER);

//        mainFrame.add(menuBar);
//        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(textPanel);
        mainFrame.add(outputPanel);
        mainFrame.setVisible(true);
        //statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        //barPanel = new JPanel(new GridLayout(2,1));
        //barPanel.add(buttonPanel);

        // mainFrame.add(headerLabel);
        //mainFrame.add(buttonPanel);
    }

    private void showGrocery() {
//        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("Submit");
//        JButton submitButton = new JButton("Print");
        JButton clearButton = new JButton("Clear");

        okButton.setActionCommand("Submit");
//        submitButton.setActionCommand("Print");
        clearButton.setActionCommand("Clear");

        okButton.addActionListener(new ButtonClickListener());
//        submitButton.addActionListener(new ButtonClickListener());
        clearButton.addActionListener(new ButtonClickListener());

        buttonPanel.add(okButton);
//        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        mainFrame.setVisible(true);
    }

    public static void pull() throws ParseException {
        String output = "";
        String totalJson="";
        try {

            String input = inputText;

            String URLFormatter = "https://api.wit.ai/message?v=20230604&q=" + URLEncoder.encode(input, StandardCharsets.UTF_8);

            URL url = new URL(URLFormatter);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer PUOTA6ZQPXYS2A2V3HZNXNLYW4DNDYNC");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                totalJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totalJson);

        try {
            //System.out.println(jsonObject.get("name"));

            org.json.simple.JSONObject entites =(org.json.simple.JSONObject) jsonObject.get("entities");
            org.json.simple.JSONArray intents =(org.json.simple.JSONArray) jsonObject.get("intents");

            org.json.simple.JSONArray agendaEntry =(org.json.simple.JSONArray) entites.get("wit$agenda_entry:agenda_entry");

//            double parseConfidence = 0;
//            String term = null;
//
//            double actionConfidence = 0;
//            String action = null;

            for (int i = 0; i < agendaEntry.size(); i++) {
                JSONObject temp;
                temp = (JSONObject) agendaEntry.get(i);

                parseConfidence = (double) temp.get("confidence");
                term = (String)temp.get("value");
            }

            for (int i = 0; i < intents.size(); i++) {
                JSONObject temp;
                temp = (JSONObject) intents.get(i);

                actionConfidence = (double) temp.get("confidence");
                action = (String)temp.get("name");
            }

            System.out.println("Parse Confidence: " + parseConfidence);
            System.out.println("Term: " + term);

            System.out.println("Action Confidence: " + actionConfidence);
            System.out.println("Action: " + action);

            if(Objects.equals(action, "add_item")){
                groceryList.add(term);

                processTextArea.setText("Identified keyword: " + term + "\n" + "Confidence of parsing the keyword: " + parseConfidence + "\n" + "\n" + "Identified action: " + action + "\n" + "Confidence: " + actionConfidence);
                outputTextArea.setText(String.valueOf(String.valueOf(groceryList)));
                System.out.println(groceryList);
            }
            if(Objects.equals(action, "remove_item")){

                groceryList.remove(term);

                processTextArea.setText("Identified keyword: " + term + "\n" + "Confidence of parsing the keyword: " + parseConfidence + "\n" + "\n" + "Identified action: " + action + "\n" + "Confidence: " + actionConfidence);
                outputTextArea.setText(String.valueOf(String.valueOf(groceryList)));
                System.out.println(groceryList);
            }
            if(Objects.equals(action, "place_order")){
                processTextArea.setText("Identified keyword: " + term + "\n" + "Confidence of parsing the keyword: " + parseConfidence + "\n" + "\n" + "Identified action: " + action + "\n" + "Confidence: " + actionConfidence);
                outputTextArea.setText("Order placed!");
                System.out.println(groceryList);
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static void main(String args[]) throws ParseException {
        GroceryList grocerylist = new GroceryList();
        grocerylist.showGrocery();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Submit")) {

                inputText = inputTextArea.getText();

                try {
                    pull();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (command.equals("Print")) {

            } else if (command.equals("Clear")) {
                groceryList.clear();

                //outputTextArea.setText("Clear Button clicked.");
                inputTextArea.setText("");
                processTextArea.setText("");
//                urlLabel.setText("");
//                searchLabel.setText("");
                outputTextArea.setText("");
            }
        }
    }

}

