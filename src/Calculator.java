import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.FlowLayout;
import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Calculator extends JFrame {
	private JPanel container = new JPanel();
	String[] tab_string = {"MC","MR","M+","M-","\u03C0", "C", "AC",
						   "7", "8", "9", "/", "\u221A", "cos","acos",
						   "4", "5", "6", "*", "%", "sin","asin",
						   "1", "2", "3", "-","1/x","tan","atan",
						   ".", "0", "=", "+", "x²", "ln","e"};
	JButton[] tab_button = new JButton[tab_string.length];
	private JLabel screen = new JLabel();
	private Dimension dim = new Dimension(60, 45);
	private double nb;
	private boolean op_select = false, update = false;
	private String operator = "";
	private double mnb = 0;

	public Calculator(){
		this.setSize(420, 300);
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(new Color(32, 32, 32));
		initComposant();
		this.setContentPane(container);
	    this.setVisible(true);
	}
	
    public void setScreen(String str) {
    	String sub;
    	if (str.length() > 1)
    		sub = new String(str.substring(str.length() - 2));
    	else
    		sub = new String("00");
    	if (sub.equals(".0"))
        	str = str.substring(0, str.length() - 2);
        if (str.equals("Infinity"))
        	str = "Error";
        if (str.equals("NaN") || str.equals("-NaN"))
        	str = "0";
        screen.setText(str);
    }
	private void initComposant(){
		Font police = new Font("Verdana", Font.PLAIN, 20);
		screen = new JLabel("0");
		screen.setFont(police);
		screen.setBackground(new Color(0, 0, 0, 0));
		screen.setHorizontalAlignment(JLabel.RIGHT);
		screen.setPreferredSize(new Dimension(400, 30));
		JPanel pad = new JPanel();    
		pad.setPreferredSize(new Dimension(420, 225));
		((FlowLayout)pad.getLayout()).setVgap(0);
		((FlowLayout)pad.getLayout()).setHgap(0);
		for(int i = 0; i < tab_string.length; i++){
			tab_button[i] = new JButton(tab_string[i]);
			tab_button[i].setPreferredSize(dim);
			tab_button[i].setBackground(Color.gray);
			tab_button[i].setFont(new Font("Verdana", Font.BOLD, 10));
			switch(i){
				case 0:
					tab_button[i].addActionListener(new MCListener());
					pad.add(tab_button[i]);
					break;
				case 1:
					tab_button[i].addActionListener(new MRListener());
					pad.add(tab_button[i]);
					break;
				case 2:
					tab_button[i].addActionListener(new MaddListener());
					pad.add(tab_button[i]);
					break;
				case 3:
					tab_button[i].addActionListener(new MsumListener());
					pad.add(tab_button[i]);
					break;
				case 4:
					tab_button[i].addActionListener(new PiListener());
					pad.add(tab_button[i]);
					break;
				case 5:
					tab_button[i].addActionListener(new DelListener());
					pad.add(tab_button[i]);
					break;
				case 6:
					tab_button[i].setBackground(Color.red);
					tab_button[i].addActionListener(new ResetListener());
					pad.add(tab_button[i]);
					break;
				case 10:
					tab_button[i].addActionListener(new DivListener());
					pad.add(tab_button[i]);
					break;
				case 11:
					tab_button[i].addActionListener(new SqrtListener());
					pad.add(tab_button[i]);
					break;
				case 12:
					tab_button[i].addActionListener(new CosListener());
					pad.add(tab_button[i]);
					break;
				case 13:
					tab_button[i].addActionListener(new AcosListener());
					pad.add(tab_button[i]);
					break;
				case 17:	
					tab_button[i].addActionListener(new MultiListener());
					pad.add(tab_button[i]);
					break;
				case 18:
					tab_button[i].addActionListener(new ModListener());
					pad.add(tab_button[i]);
					break;
				case 19:
					tab_button[i].addActionListener(new SinListener());
					pad.add(tab_button[i]);
					break;
				case 20:
					tab_button[i].addActionListener(new AsinListener());
					pad.add(tab_button[i]);
					break;
				case 24:
					tab_button[i].addActionListener(new MinusListener());
					pad.add(tab_button[i]);
					break;	
				case 25:
					tab_button[i].addActionListener(new RecListener());
					pad.add(tab_button[i]);
					break;
				case 26:
					tab_button[i].addActionListener(new TanListener());
					pad.add(tab_button[i]);
					break;
				case 27:
					tab_button[i].addActionListener(new AtanListener());
					pad.add(tab_button[i]);
					break;
				case 30:
					tab_button[i].setBackground(Color.orange);
					tab_button[i].addActionListener(new EqualListener());
					pad.add(tab_button[i]);
					break;
				case 31:
					tab_button[i].addActionListener(new PlusListener());
					pad.add(tab_button[i]);
					break;
				case 32:
					tab_button[i].addActionListener(new SquareListener());
					pad.add(tab_button[i]);
					break;
				case 33:
					tab_button[i].addActionListener(new LogListener());
					pad.add(tab_button[i]);
					break;
				case 34:
					tab_button[i].addActionListener(new EListener());
					pad.add(tab_button[i]);
					break;
				default :
					tab_button[i].setBackground(Color.lightGray);
					pad.add(tab_button[i]);
					tab_button[i].addActionListener(new NumberListener());
					break;
			}
		}
		container.add(screen, BorderLayout.NORTH);
		container.add(pad, BorderLayout.CENTER);
	}

	private int calcul(){
		double ret;
		if ((screen.getText()).equals("Error") || (String.valueOf(nb)).equals("Infinity"))
			return -1;
		ret = operator.equals("+") ? nb = nb + Double.valueOf(screen.getText()).doubleValue() : 0;        
		ret = operator.equals("-") ? nb = nb - Double.valueOf(screen.getText()).doubleValue() : 0;        
		ret = operator.equals("*") ? nb = nb * Double.valueOf(screen.getText()).doubleValue() : 0;
		if(operator.equals("/")){
			try{
				nb = nb / Double.valueOf(screen.getText()).doubleValue();
			} catch(ArithmeticException e) {
				nb = 0;
				return -1;
			}
		}
		if(operator.equals("%")){
			try{
				nb = nb % Double.valueOf(screen.getText()).doubleValue();
			} catch(ArithmeticException e) {
				nb = 0;
				return -1;
			}
		}
		if(operator.equals("rec")){
			try{
				nb = 1 / nb;
			} catch(ArithmeticException e) {
				nb = 0;
				return -1;
			}
		}
		ret = operator.equals("sqrt") ? nb = Math.sqrt(nb) : 0;	
		ret = operator.equals("cos") ? nb = Math.cos(nb) : 0;
		ret = operator.equals("sin") ? nb = Math.sin(nb) : 0;
		ret = operator.equals("tan") ? nb = Math.tan(nb) : 0;
		ret = operator.equals("acos") ? nb = Math.acos(nb) : 0;
		ret = operator.equals("asin") ? nb = Math.asin(nb) : 0;
		ret = operator.equals("atan") ? nb = Math.atan(nb) : 0;
		ret = operator.equals("sq") ? nb = nb * nb : 0;
		ret = operator.equals("log") ? nb = Math.log(nb) : 0;
		ret = operator.equals("pi") ? nb = Math.PI : 0;
		ret = operator.equals("e") ? nb = Math.E : 0;
		return 1;
	}

	class MCListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			mnb = 0;
			update = true;
			op_select = false;
		}
	}
	class MRListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Calculator.this.setScreen(String.valueOf(mnb));
			update = true;
			op_select = false;
		}
	}
	class MaddListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			mnb = mnb + Double.valueOf(screen.getText()).doubleValue();
			update = true;
			op_select = false;
		}
	}
	class MsumListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			mnb = mnb - Double.valueOf(screen.getText()).doubleValue();
			update = true;
			op_select = false;
		}
	}

	class NumberListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String str = ((JButton)e.getSource()).getText();
			if(update){
				update = false;
			} else {
				if(!screen.getText().equals("0"))
					str = screen.getText() + str;
		      	}
				if (str.charAt(0) == '.')
					str = "0" + str;
				if (str.length() - str.replace(".", "").length() < 2)
					screen.setText(str);
			}
		}
	class EqualListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			update = true;
			op_select = false;
		}
	}
	class SqrtListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "sqrt";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class CosListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "cos";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class SinListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "sin";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class TanListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "tan";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class AcosListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "acos";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class AsinListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "asin";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class AtanListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "atan";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class SquareListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "sq";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class LogListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "log";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class PiListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "pi";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class EListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "e";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class RecListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			nb = Double.valueOf(screen.getText()).doubleValue();
			operator = "rec";
			if (calcul() == -1)
				Calculator.this.setScreen("Error");
			else
				Calculator.this.setScreen(String.valueOf(nb));
			op_select = false;
			update = true;
		}
	}
	class PlusListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(op_select){
				if (calcul() == -1)
					Calculator.this.setScreen("Error");
				else
					Calculator.this.setScreen(String.valueOf(nb));
			}
			else{
				nb = Double.valueOf(screen.getText()).doubleValue();
				op_select = true;
			}
			operator = "+";
			update = true;
		}
	}
	class MinusListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (screen.getText().charAt(0) == '0' && screen.getText().length() == 1) {
				Calculator.this.setScreen("-");					
				op_select = false;
				update = false;
				return;
			}
			if(op_select){
				if (calcul() == -1)
					Calculator.this.setScreen("Error");
				else
					Calculator.this.setScreen(String.valueOf(nb));
			}
			else{
				nb = Double.valueOf(screen.getText()).doubleValue();
				op_select = true;
			}
			operator = "-";
			update = true;
		}
	}
	class MultiListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(op_select){
				if (calcul() == -1)
					Calculator.this.setScreen("Error");
				else
					Calculator.this.setScreen(String.valueOf(nb));
			}
			else{
				nb = Double.valueOf(screen.getText()).doubleValue();
				op_select = true;
			}
			operator = "*";
			update = true;
		}
	}
	class DivListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(op_select){
				if (calcul() == -1)
					Calculator.this.setScreen("Error");
				else
					Calculator.this.setScreen(String.valueOf(nb));
			}
			else{
				nb = Double.valueOf(screen.getText()).doubleValue();
				op_select = true;
			}
			operator = "/";
			update = true;
		}
	}
	class ModListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(op_select){
				if (calcul() == -1)
					Calculator.this.setScreen("Error");
				else
					Calculator.this.setScreen(String.valueOf(nb));
			}
			else{
				nb = Double.valueOf(screen.getText()).doubleValue();
				op_select = true;
			}
			operator = "%";
			update = true;
		}
	}
	class DelListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String str;
			op_select = false;
			update = true;
			nb = 0;
			operator = "";
			str = screen.getText();
			if (str.length() > 0 && !str.equals("Error")) {
				if (str.length() == 1)
					str = "0";
				else
					str = str.substring(0, str.length() - 1);
			}
			screen.setText(str);

		}
	}      
	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			op_select = false;
			update = true;
			nb = 0;
			operator = "";
			screen.setText("0");
		}
	}      
}