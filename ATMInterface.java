package ui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class ATMInterface extends JFrame {

    /* ---------- DAO & session ---------- */
    private final UserDAO userDAO = new UserDAO();
    private User currentUser;

    /* ---------- theme colors (same as Car Rental UI) ---------- */
    private static final Color HEADER_BLUE  = new Color(70,130,180);  // banner
    private static final Color BTN_BLUE     = new Color(100,149,237); // buttons
    private static final Color PANEL_BG     = new Color(245,245,245); // form area

    /* ---------- misc ---------- */
    private JLabel animatedLabel;
    private int dotCount = 0;

    /* ==========================================================
                      CONSTRUCTOR & ENTRY
       ========================================================== */
    public ATMInterface() {
        setTitle("ATM Interface System 🪙");
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        showAnimatedWelcome();   // first screen
    }

    /* ==========================================================
                       SHARED HEADER MAKER
       ========================================================== */
    private JLabel makeHeader(String text) {
        JLabel h = new JLabel(text, SwingConstants.CENTER);
        h.setFont(new Font("Segoe UI", Font.BOLD, 26));
        h.setOpaque(true);
        h.setBackground(HEADER_BLUE);
        h.setForeground(Color.WHITE);
        h.setBorder(new EmptyBorder(10,0,10,0));
        return h;
    }

    /* ==========================================================
                          ANIMATED WELCOME
       ========================================================== */
    private void showAnimatedWelcome() {
        getContentPane().removeAll();

        JPanel p = new JPanel(new BorderLayout());
        p.add(makeHeader("🪙 ATM Interface System"), BorderLayout.NORTH);

        // dark banner style body
        p.setBackground(Color.BLACK);
        animatedLabel = new JLabel("Loading", SwingConstants.CENTER);
        animatedLabel.setForeground(Color.GREEN);
        animatedLabel.setFont(new Font("Courier New", Font.PLAIN, 22));
        p.add(animatedLabel, BorderLayout.CENTER);

        setContentPane(p);
        revalidate(); repaint();

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    dotCount = (dotCount + 1) % 4;
                    animatedLabel.setText("Loading" + ".".repeat(dotCount));
                });
            }
        },0,500);

        javax.swing.Timer next = new javax.swing.Timer(2500,e->{ t.cancel(); showStartScreen();});
        next.setRepeats(false); next.start();
    }

    /* ==========================================================
                     START SCREEN – choose mode
       ========================================================== */
    private void showStartScreen() {
        getContentPane().removeAll();

        JPanel body = new JPanel(new BorderLayout());
        body.add(makeHeader("Select Transaction Mode"), BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(2,1,10,10));
        btnPanel.setBackground(PANEL_BG);
        btnPanel.setBorder(new EmptyBorder(60,60,60,60));

        JButton card = styledButton("Card Transaction");
        JButton cardless = styledButton("Cardless Transaction");
        btnPanel.add(card); btnPanel.add(cardless);

        card.addActionListener(e -> showCardLogin());
        cardless.addActionListener(e -> showCardlessLogin());

        body.add(btnPanel, BorderLayout.CENTER);
        setContentPane(body);
        revalidate(); repaint();
    }

    /* ==========================================================
                        CARD LOGIN  PANEL
       ========================================================== */
    private void showCardLogin() { buildLoginPanel(
            "Card Login",
            new String[]{"Card Number:", "PIN:"},
            (vals)-> tryLogin(userDAO.validateCardLogin(vals[0], vals[1]))
    ); }

    /* ==========================================================
                     CARDLESS LOGIN PANEL
       ========================================================== */
    private void showCardlessLogin() { buildLoginPanel(
            "Cardless Login",
            new String[]{"Account Number:", "E‑Banking PIN:"},
            (vals)-> tryLogin(userDAO.validateCardlessLogin(vals[0], vals[1]))
    ); }

    /* ---------- helper to build either login screen ---------- */
    private interface LoginHandler { void handle(String[] vals); }
    private void buildLoginPanel(String title, String[] labels, LoginHandler handler){
        getContentPane().removeAll();

        JPanel root = new JPanel(new BorderLayout());
        root.add(makeHeader("🪙 " + title), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL_BG);
        form.setBorder(new EmptyBorder(20,30,20,30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6); g.anchor = GridBagConstraints.WEST;

        JTextField[] fields = { new JTextField(14), new JPasswordField(14) };
        for(int i=0;i<labels.length;i++){
            g.gridx=0; g.gridy=i; form.add(new JLabel(labels[i]),g);
            g.gridx=1; form.add(fields[i],g);
        }

        JButton back = styledButton("Back");
        JButton login = styledButton("Login");
        back.addActionListener(e-> showStartScreen());
        login.addActionListener(e->{
            String[] vals = { fields[0].getText().trim(), new String(((JPasswordField)fields[1]).getPassword()) };
            handler.handle(vals);
        });

        g.gridy = labels.length; g.gridx=0; form.add(back,g);
        g.gridx=1; form.add(login,g);

        root.add(form, BorderLayout.CENTER);
        setContentPane(root);
        revalidate(); repaint();
    }

    /* ---------- attempt login & branch ---------- */
    private void tryLogin(User u){
        if(u!=null){ currentUser=u; showWelcomeScreen(); }
        else JOptionPane.showMessageDialog(this,"Invalid credentials","Error",JOptionPane.ERROR_MESSAGE);
    }

    /* ==========================================================
               TYPING‐EFFECT WELCOME (colors changed)
       ========================================================== */
    private void showWelcomeScreen() {
        JPanel root = new JPanel(new BorderLayout());
        root.add(makeHeader("Welcome"), BorderLayout.NORTH);
        root.setBackground(PANEL_BG);

        JLabel typing = new JLabel("",SwingConstants.CENTER);
        typing.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JLabel acc   = new JLabel("",SwingConstants.CENTER);
        acc.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        root.add(typing,BorderLayout.CENTER);
        root.add(acc,  BorderLayout.SOUTH);

        setContentPane(root); revalidate(); repaint();

        String msg = "Hello, " + currentUser.getName();
        int[] idx={0};
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                SwingUtilities.invokeLater(()->{
                    if(idx[0] < msg.length()){
                        typing.setText(msg.substring(0,idx[0]+1)); idx[0]++;
                    }else{
                        t.cancel();
                        acc.setText("Account Number: " + currentUser.getAccountNumber());
                        new javax.swing.Timer(1200,e-> showATMOptions()).start();
                    }
                });
            }
        },0,90);
    }

    /* ==========================================================
                    POST‑LOGIN  DASHBOARD
       ========================================================== */
    private void showATMOptions(){
        getContentPane().removeAll();

        JPanel root = new JPanel(new BorderLayout());
        root.add(makeHeader("ATM Dashboard"), BorderLayout.NORTH);

        /* --- right‑hand button column --- */
        JPanel btnCol = new JPanel(new GridLayout(7,1,8,8));
        btnCol.setBackground(PANEL_BG);
        btnCol.setBorder(new EmptyBorder(20,20,20,20));

        JButton deposit   = styledButton("Deposit");
        JButton withdraw  = styledButton("Withdraw");
        JButton balance   = styledButton("Check Balance");
        JButton chatbot   = styledButton("Chatbot Help");
        JButton logout    = styledButton("Logout");
        JButton quit      = styledButton("Quit");

        btnCol.add(deposit);
        btnCol.add(withdraw);
        btnCol.add(balance);
        btnCol.add(chatbot);
        btnCol.add(logout);
        btnCol.add(quit);

        // Fill center with a blank light area (could host future tables)
        JPanel placeholder = new JPanel();
        placeholder.setBackground(Color.WHITE);

        root.add(placeholder, BorderLayout.CENTER);
        root.add(btnCol,     BorderLayout.EAST);

        /* ---- actions ---- */
        deposit.addActionListener(e-> deposit());
        withdraw.addActionListener(e-> withdraw());
        balance.addActionListener(e-> JOptionPane.showMessageDialog(this,"Balance: ₹"+currentUser.getBalance()));
        chatbot.addActionListener(e-> showAIChatbot());
        logout.addActionListener(e->{ currentUser=null; showStartScreen(); });
        quit.addActionListener(e-> showExitAnimation());

        setContentPane(root);
        revalidate(); repaint();
    }

    /* ==========================================================
                  BUTTON STYLER  (blue theme)
       ========================================================== */
    private JButton styledButton(String text){
        JButton b = new JButton(text);
        b.setBackground(BTN_BLUE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return b;
    }

    /* ==========================================================
             DEPOSIT & WITHDRAW  (logic unchanged)
       ========================================================== */
    private void deposit(){
        String in = JOptionPane.showInputDialog(this,"Enter deposit amount:");
        try{
            double amt = Double.parseDouble(in);
            if(amt<=0) throw new NumberFormatException();
            currentUser.setBalance(currentUser.getBalance()+amt);
            userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance());
            JOptionPane.showMessageDialog(this,"₹"+amt+" deposited!");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid amount","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw(){
        String in = JOptionPane.showInputDialog(this,"Enter withdrawal amount:");
        try{
            double amt = Double.parseDouble(in);
            if(amt<=0) throw new NumberFormatException();
            if(amt>currentUser.getBalance()){
                JOptionPane.showMessageDialog(this,"Insufficient balance");
                return;
            }
            int ok = JOptionPane.showConfirmDialog(this,"Withdraw ₹"+amt+"?","Confirm",JOptionPane.YES_NO_OPTION);
            if(ok==JOptionPane.YES_OPTION){
                currentUser.setBalance(currentUser.getBalance()-amt);
                userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance());
                JOptionPane.showMessageDialog(this,"₹"+amt+" withdrawn!");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid amount","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    /* ==========================================================
                        CHATBOT  (unchanged)
       ========================================================== */
    private void showAIChatbot(){
        JFrame f = new JFrame("AI Assistant");
        f.setSize(400,400); f.setLocationRelativeTo(this);
        f.setLayout(new BorderLayout());

        JTextArea chat = new JTextArea(); chat.setEditable(false); chat.setLineWrap(true);
        JScrollPane pane = new JScrollPane(chat);
        JTextField input = new JTextField();

        input.addActionListener(e->{
            String m = input.getText().trim();
            if(!m.isEmpty()){
                chat.append("You: "+m+"\n");
                chat.append("AI: "+getBotReply(m)+"\n\n");
                input.setText("");
            }
        });

        f.add(pane,BorderLayout.CENTER);
        f.add(input,BorderLayout.SOUTH);
        f.setVisible(true);
    }

    private String getBotReply(String m){
        m=m.toLowerCase();
        if(m.contains("deposit"))   return "Click the Deposit button and enter the amount.";
        if(m.contains("withdraw"))  return "Select Withdraw and confirm the amount.";
        if(m.contains("balance"))   return "Use the Check Balance button to view current funds.";
        return "Sorry, I didn't understand. Try keywords like deposit, withdraw, balance.";
    }

    /* ==========================================================
                 FAREWELL ANIMATION  (blue theme)
       ========================================================== */
    private void showExitAnimation(){
        JPanel p = new JPanel(new BorderLayout());
        p.add(makeHeader("Thank You"), BorderLayout.NORTH);
        p.setBackground(Color.BLACK);

        JLabel msg = new JLabel("",SwingConstants.CENTER);
        msg.setFont(new Font("Courier New", Font.BOLD, 22));
        msg.setForeground(Color.GREEN);
        p.add(msg,BorderLayout.CENTER);

        setContentPane(p); revalidate(); repaint();

        String text = "Thank you for using our ATM. Hope you liked the service.";
        int[] idx={0};
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                SwingUtilities.invokeLater(()->{
                    if(idx[0]<text.length()){
                        msg.setText(text.substring(0,idx[0]+1));
                        idx[0]++;
                    }else t.cancel();
                });
            }
        },0,90);
    }

    /* ==========================================================
                        MAIN  (launcher)
       ========================================================== */
    public static void main(String[] args){
        SwingUtilities.invokeLater(ATMInterface::new);
    }
}
