package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dal.ModuloConexao;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {

		/**
		 * inicialização dos componentes da tela de login
		 */

		setTitle("Sistema RM");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaLogin.class.getResource("/icons/iconRM.png")));

		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Usuário");
		lblNewLabel.setBounds(102, 60, 117, 14);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(237, 57, 113, 20);
		txtUsuario.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnLogin.setBounds(274, 216, 76, 23);

		JLabel lblStatus = new JLabel("");
		lblStatus.setBounds(102, 143, 56, 71);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setIcon(new ImageIcon(TelaLogin.class.getResource("/icons/banco_ok.png")));

		txtPassword = new JPasswordField();
		txtPassword.setBounds(237, 115, 113, 20);
		getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(102, 118, 117, 14);
		getContentPane().add(lblNewLabel_1);
		getContentPane().add(lblNewLabel);
		getContentPane().add(lblStatus);
		getContentPane().add(txtUsuario);
		getContentPane().add(txtPassword);
		getContentPane().add(btnLogin);

		JLabel lblStatusBD = new JLabel("");
		lblStatusBD.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatusBD.setBounds(10, 225, 230, 14);
		getContentPane().add(lblStatusBD);

		conexao = ModuloConexao.conector();
		// System.out.println(conexao);
		if (conexao != null) {
			lblStatusBD.setText("Banco de Dados Conectado");
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/banco_ok.png")));
		} else {
			lblStatusBD.setText("Banco de Dados Não está Conectado");
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/banco_error.png")));
		}

	}

	public void logar() {
		String sql = "select * from tbusuarios where login=? and senha=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtPassword.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				TelaPrincipal principal = new TelaPrincipal();
				principal.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválido.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
