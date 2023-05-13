/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.caiohenrique.comtainerr.telas;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import javax.swing.JOptionPane;
import com.caiohenrique.comtainerr.DAO.ModuloConexao;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author cahen
 */
public class TelaPrincipal extends javax.swing.JFrame {

    Connection conexao = null;

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        mudarData();
        conexao = ModuloConexao.conector();
        Image iconejanela = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/caiohenrique/comtainerr/icons/logotipo.png"));
        this.setIconImage(iconejanela);

    }

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Desktop = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        menubar = new javax.swing.JMenuBar();
        menu1Cad = new javax.swing.JMenu();
        menuCadCliente = new javax.swing.JMenuItem();
        menuCadServico = new javax.swing.JMenuItem();
        menuCadUser = new javax.swing.JMenuItem();
        menu2Rel = new javax.swing.JMenu();
        menuRelService = new javax.swing.JMenuItem();
        menuRelCli = new javax.swing.JMenuItem();
        menu3Ajuda = new javax.swing.JMenu();
        menuAjudaSobre = new javax.swing.JMenuItem();
        menu4opt = new javax.swing.JMenu();
        menuOptSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMTAINERR - FUNILARIA E PINTURA");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        javax.swing.GroupLayout DesktopLayout = new javax.swing.GroupLayout(Desktop);
        Desktop.setLayout(DesktopLayout);
        DesktopLayout.setHorizontalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        DesktopLayout.setVerticalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/logo1.png"))); // NOI18N

        lblData.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-calendário-20.png"))); // NOI18N
        lblData.setText("DATA");

        lblUser.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        lblUser.setForeground(new java.awt.Color(0, 51, 51));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-usuário-20.png"))); // NOI18N
        lblUser.setText("USER");

        lblPerfil.setFont(new java.awt.Font("Gadugi", 1, 20)); // NOI18N
        lblPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPerfil.setText("PERFIL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Desktop, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPerfil)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu1Cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/cadastro_1.png"))); // NOI18N
        menu1Cad.setText("Cadastrar");

        menuCadCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCadCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/cadastrarClient.png"))); // NOI18N
        menuCadCliente.setText("Cliente");
        menuCadCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadClienteActionPerformed(evt);
            }
        });
        menu1Cad.add(menuCadCliente);

        menuCadServico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCadServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/service.png"))); // NOI18N
        menuCadServico.setText("Serviço");
        menuCadServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadServicoActionPerformed(evt);
            }
        });
        menu1Cad.add(menuCadServico);

        menuCadUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCadUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/user.png"))); // NOI18N
        menuCadUser.setText("Usuário");
        menuCadUser.setEnabled(false);
        menuCadUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadUserActionPerformed(evt);
            }
        });
        menu1Cad.add(menuCadUser);

        menubar.add(menu1Cad);

        menu2Rel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/relatorio.png"))); // NOI18N
        menu2Rel.setText("Relatório");
        menu2Rel.setEnabled(false);

        menuRelService.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuRelService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/service.png"))); // NOI18N
        menuRelService.setText("Serviços");
        menuRelService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelServiceActionPerformed(evt);
            }
        });
        menu2Rel.add(menuRelService);

        menuRelCli.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuRelCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-lote-de-atribuir-15.png"))); // NOI18N
        menuRelCli.setText("Clientes");
        menuRelCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelCliActionPerformed(evt);
            }
        });
        menu2Rel.add(menuRelCli);

        menubar.add(menu2Rel);

        menu3Ajuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/ajuda.png"))); // NOI18N
        menu3Ajuda.setText("Ajuda");

        menuAjudaSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        menuAjudaSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/info.png"))); // NOI18N
        menuAjudaSobre.setText("Sobre o sistema");
        menuAjudaSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAjudaSobreActionPerformed(evt);
            }
        });
        menu3Ajuda.add(menuAjudaSobre);

        menubar.add(menu3Ajuda);

        menu4opt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/config.png"))); // NOI18N
        menu4opt.setText("Opções");
        menu4opt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu4optActionPerformed(evt);
            }
        });

        menuOptSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        menuOptSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/sair.png"))); // NOI18N
        menuOptSair.setText("Sair do sistema");
        menuOptSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOptSairActionPerformed(evt);
            }
        });
        menu4opt.add(menuOptSair);

        menubar.add(menu4opt);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menu4optActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu4optActionPerformed

    }//GEN-LAST:event_menu4optActionPerformed

    private void menuOptSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOptSairActionPerformed
        int sairDoSistema = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair para a área de trabalho ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sairDoSistema == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuOptSairActionPerformed

    private void menuAjudaSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAjudaSobreActionPerformed
        // Chamar a tela de sobre
        Sobre sobre = new Sobre();
        sobre.setVisible(true);
    }//GEN-LAST:event_menuAjudaSobreActionPerformed

    private void menuCadUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadUserActionPerformed
        // Chamar o formulario de cadastro de usuarios dentro do JDesktopPanel
        cadastroUsuarios telausuario = new cadastroUsuarios();
        telausuario.setVisible(true);
        Desktop.add(telausuario);
    }//GEN-LAST:event_menuCadUserActionPerformed

    private void menuCadClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadClienteActionPerformed
        // Chamando tela de cliente
        cadastroCliente cadastrocliente = new cadastroCliente();
        cadastrocliente.setVisible(true);
        Desktop.add(cadastrocliente);
    }//GEN-LAST:event_menuCadClienteActionPerformed

    private void menuRelServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelServiceActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
            try {
                JasperReport relatorio = JasperCompileManager.compileReport("C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorios_servicos.jrxml");
                Map<String,Object> parametros = new HashMap<>();
                JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, conexao);
                JasperExportManager.exportReportToPdfFile(print,"C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorio1.pdf" );
                Runtime.getRuntime().exec(new String []{"C:/Program Files/Google/Chrome/Application/chrome.exe","C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorio1.pdf"});
            } catch (JRException | IOException vaca ) {
                System.out.println("Problema no banco.");   
            }
        }
    }//GEN-LAST:event_menuRelServiceActionPerformed

    private void menuCadServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadServicoActionPerformed
        // Chamar tela de cadastrar serviços
        cadastroServicos cadastroservico = new cadastroServicos();
        cadastroservico.setVisible(true);
        Desktop.add(cadastroservico);
    }//GEN-LAST:event_menuCadServicoActionPerformed

    private void menuRelCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelCliActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
            try {
                JasperReport relatorio = JasperCompileManager.compileReport("C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorios_clientes.jrxml");
                Map<String,Object> parametros = new HashMap<>();
                JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, conexao);
                JasperExportManager.exportReportToPdfFile(print,"C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorio.pdf" );
                Runtime.getRuntime().exec(new String []{"C:/Program Files/Google/Chrome/Application/chrome.exe","C:/Users/Felipe/Desktop/ProjetoComteinerr-master/src/com/caiohenrique/comtainerr/DAO/reports/relatorio.pdf"});
            } catch (JRException | IOException vaca ) {
                System.out.println("Problema no banco.");   
            }
        }
    }//GEN-LAST:event_menuRelCliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Desktop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblPerfil;
    public static javax.swing.JLabel lblUser;
    private javax.swing.JMenu menu1Cad;
    public static javax.swing.JMenu menu2Rel;
    private javax.swing.JMenu menu3Ajuda;
    private javax.swing.JMenu menu4opt;
    private javax.swing.JMenuItem menuAjudaSobre;
    private javax.swing.JMenuItem menuCadCliente;
    private javax.swing.JMenuItem menuCadServico;
    public static javax.swing.JMenuItem menuCadUser;
    private javax.swing.JMenuItem menuOptSair;
    private javax.swing.JMenuItem menuRelCli;
    private javax.swing.JMenuItem menuRelService;
    private javax.swing.JMenuBar menubar;
    // End of variables declaration//GEN-END:variables
private void mudarData() {
        Date date = new Date();
        DateFormat formatar = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatar.format(date));
    }

}