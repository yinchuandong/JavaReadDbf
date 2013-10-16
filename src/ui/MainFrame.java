package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import model.Transform;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton exportBtn;
	private JButton fileChooseBtn;

	private Transform transform;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initComponents();
		bindEvents();
		
		transform = new Transform();
	}
	
	private void bindEvents(){
		fileChooseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				fileChooseBtnEvent(event);
			}
		});
		
		exportBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				exportBtnEvent(event);
			}
		});
	}
	
	private void initComponents(){
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 448, 123);
		contentPane.add(panel);
		panel.setLayout(null);
		
		fileChooseBtn = new JButton("选择文件");
		fileChooseBtn.setBounds(257, 32, 116, 25);
		panel.add(fileChooseBtn);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(23, 32, 217, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		exportBtn = new JButton("导出excel");
		exportBtn.setBounds(257, 82, 116, 25);
		panel.add(exportBtn);
	}
	
	/**
	 * 文件选择按钮的响应时间
	 * @param event
	 */
	private void fileChooseBtnEvent(ActionEvent event){
		JFileChooser  fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
		if (file != null) {
			String path = file.getAbsolutePath();
			textField.setText(path);
			transform.readDBF(file);
			transform.calulate();
		}else{
			textField.setText("");
		}
		
		
	}
	
	private void exportBtnEvent(ActionEvent event) {
		JFileChooser chooser = new JFileChooser("文件保存");
		JTextField textField = getTextField(chooser);
		textField.setText("新建文件.xls");
        chooser.showSaveDialog(textField);
        File file = chooser.getSelectedFile();
        if (file != null) {
        	transform.printExcel(file);
		}else{
			textField.setText("");
		}
	}
	
	public JTextField getTextField(Container c) {
        JTextField textField = null;
        for (int i = 0; i < c.getComponentCount(); i++) {
            Component cnt = c.getComponent(i);
            if (cnt instanceof JTextField) {
                return (JTextField) cnt;
            }
            if (cnt instanceof Container) {
                textField = getTextField((Container) cnt);
                if (textField != null) {
                    return textField;
                }
            }
        }
        return textField;
    }
	
}
