/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.caiohenrique.comtainerr.telas;

import java.sql.*;
import com.caiohenrique.comtainerr.DAO.ModuloConexao;
import javax.swing.JOptionPane;
// Recurso da biblioteca rs2xml.jar para preencher a tabela do formulario
import net.proteanit.sql.DbUtils;


/**
 *
 * @author cahen
 */
public class cadastroCliente extends javax.swing.JInternalFrame {

    // Todo form que criar e quiser conectar com o sql farei o mesmo processo
    // Crio uma conexão nula no momento
    Connection conexao = null;
    // Manipulando instruções sql com PreparedStatement e ResultSet
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form cadastroCliente
     */
    public cadastroCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnLimpar.setVisible(false);
    }
   

    private void adicionar() {

        String sql = "INSERT INTO clientes (nomecliente,endcliente, fonecliente, emailcliente) VALUES (?,?,?,?)";
        try {
            // Preparando a conexão
            pst = conexao.prepareStatement(sql);
            // Adicionando os clientes ao banco de dados
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEndCliente.getText());
            pst.setString(3, txtTelefoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            
            // A linha abaixo atualiza a tabela cliente com os dados do nosso formulario
            // Se o usuário for adicionado, será retornado o valor 1, caso contrário retornará 0

            if (!txtNomeCliente.getText().equals("") && !txtTelefoneCliente.getText().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso !");
                    limparCampo();

                } else {
                    lblErro.setText("Não foi possível adicionar o cliente");
                    limparCampo();
                }
            } else {
                lblErro.setText("Não foi possível adicionar esse cliente!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            limparCampo();
        }
    }

    // Pesquisar clientes pelo nome com filtros!
    private void pesquisarClientes() {

        String sql = "SELECT idcliente as Id, nomecliente as Nome, endcliente as Endereço, fonecliente as Telefone, emailcliente as Email FROM clientes WHERE nomecliente like ?";
        if (txtBuscarCliente.getText().equals("")){
            tbCliente.setEnabled(false);
        }else{
            tbCliente.setEnabled(true);
            try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteudo da caixinha de pesquisa para o ?
            pst.setString(1, txtBuscarCliente.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca para preencher a tabela!
            tbCliente.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
        
    }

    public void setarCampos() {
        int setar = tbCliente.getSelectedRow();
        txtIdCliente.setText(tbCliente.getModel().getValueAt(setar, 0).toString());
        txtNomeCliente.setText(tbCliente.getModel().getValueAt(setar, 1).toString());
        txtEndCliente.setText(tbCliente.getModel().getValueAt(setar, 2).toString());
        txtTelefoneCliente.setText(tbCliente.getModel().getValueAt(setar, 3).toString());
        txtEmailCliente.setText(tbCliente.getModel().getValueAt(setar, 4).toString());
        // Desabilitar botao adicionar quando algum usuário já estiver adicionado
        btnCadastrarCliente.setEnabled(false);
        btnLimpar.setVisible(true);
    }

    public void limparCampo() {
        txtEmailCliente.setText(""); //
        txtEndCliente.setText("");
        txtTelefoneCliente.setText("");
        txtNomeCliente.setText("");
        txtIdCliente.setText("");
    }

    private void editar() {
        // Crio o código de update
        String sql = "UPDATE clientes SET nomecliente=?, endcliente=?, fonecliente=?, emailcliente=? where idcliente=?";
        try {
            // Preparando o código
            pst = conexao.prepareStatement(sql);
            // Adicionando os usuarios ao banco de dados
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEndCliente.getText());
            pst.setString(3, txtTelefoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            pst.setString(5, txtIdCliente.getText());

            if (!txtNomeCliente.getText().equals("") && !txtTelefoneCliente.getText().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Dados de cliente editados com sucesso !");
                    limparCampo();
                    btnCadastrarCliente.setEnabled(true);
                } else {
                    lblErro.setText("Não foi possível editar o cliente pois existe/m campo/s não preenchido/s");
                    limparCampo();
                }
            } else {
                lblErro.setText("Não foi possível editar o usuário pois existe/m campo/s não preenchido/s");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            limparCampo();
        }
    }

    // Método para deletar cliente!
    private void deletar() {
        int deletar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse cliente do banco de dados? ", "Deletar cliente", JOptionPane.YES_NO_OPTION);

        if (deletar == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM clientes where idcliente=?";
            try {
                // Preparando a conexão
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdCliente.getText());
                int apagado = pst.executeUpdate();
                // Se o usuário for deletado com sucesso do sistema ele devolve a mensagem de sucesso!.
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
                    limparCampo();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    private void limparTudo() {
        btnCadastrarCliente.setEnabled(true);
        txtBuscarCliente.setText("");
        txtIdCliente.setText("");
        txtEmailCliente.setText("");
        txtTelefoneCliente.setText("");
        txtNomeCliente.setText("");
        txtEndCliente.setText("");
        btnLimpar.setVisible(false);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        txtEndCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCliente = new javax.swing.JTable();
        btnCadastrarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnDeletarCliente = new javax.swing.JButton();
        txtBuscarCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblErro = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        txtTelefoneCliente = new javax.swing.JFormattedTextField();
        btnLimpar = new javax.swing.JButton();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastrar clientes");
        setPreferredSize(new java.awt.Dimension(750, 601));

        jPanel1.setBackground(new java.awt.Color(229, 228, 228));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-adicionar-usuário-masculino-30 (1).png"))); // NOI18N
        jLabel1.setText(" CADASTRAR CLIENTES");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("* Nome: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Endereço:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("* Celular:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("E-mail:");

        txtNomeCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNomeCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtEmailCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmailCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtEndCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEndCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbCliente = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex ){
                return false;
            }
        };
        tbCliente.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tbCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Endereço", "Telefone", "Email"
            }
        ));
        tbCliente.setFocusable(false);
        tbCliente.getTableHeader().setReorderingAllowed(false);
        tbCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCliente);

        btnCadastrarCliente.setBackground(new java.awt.Color(204, 255, 204));
        btnCadastrarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCadastrarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-mais-2-matemática-10.png"))); // NOI18N
        btnCadastrarCliente.setText("CADASTRAR");
        btnCadastrarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setBackground(new java.awt.Color(255, 255, 204));
        btnEditarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-edite-a-linha-10.png"))); // NOI18N
        btnEditarCliente.setText("EDITAR");
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnDeletarCliente.setBackground(new java.awt.Color(255, 153, 153));
        btnDeletarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeletarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-excluir-10.png"))); // NOI18N
        btnDeletarCliente.setText("DELETAR");
        btnDeletarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarClienteActionPerformed(evt);
            }
        });

        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-cliente-de-pesquisa-40.png"))); // NOI18N

        jLabel7.setText("Campos obrigatórios *");

        lblErro.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblErro.setForeground(new java.awt.Color(255, 51, 51));
        lblErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ID:");

        txtIdCliente.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtIdCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtIdCliente.setEnabled(false);

        txtTelefoneCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        try {
            txtTelefoneCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-#### ")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefoneCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnLimpar.setBackground(new java.awt.Color(204, 255, 255));
        btnLimpar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpar.setText("LIMPAR");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEndCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelefoneCliente)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCadastrarCliente))
                        .addGap(35, 35, 35)
                        .addComponent(btnEditarCliente)
                        .addGap(36, 36, 36)
                        .addComponent(btnDeletarCliente))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEndCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(lblErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLimpar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addGap(15, 15, 15)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarClienteActionPerformed
        adicionar();
    }//GEN-LAST:event_btnCadastrarClienteActionPerformed

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        // Enquanto for digitando ele vai executando o evento
        pesquisarClientes();
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void tbClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClienteMouseClicked
        // Setar campos da tabela clickando com o mouse!
        setarCampos();
        
    }//GEN-LAST:event_tbClienteMouseClicked

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnDeletarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarClienteActionPerformed
        deletar();
    }//GEN-LAST:event_btnDeletarClienteActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparTudo();
    }//GEN-LAST:event_btnLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarCliente;
    private javax.swing.JButton btnDeletarCliente;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblErro;
    private javax.swing.JTable tbCliente;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEndCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JFormattedTextField txtTelefoneCliente;
    // End of variables declaration//GEN-END:variables
}
