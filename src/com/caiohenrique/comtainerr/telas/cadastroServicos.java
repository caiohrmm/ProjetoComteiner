/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.caiohenrique.comtainerr.telas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.caiohenrique.comtainerr.DAO.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author cahen
 */
public class cadastroServicos extends javax.swing.JInternalFrame {

    /**
     * Creates new form Serviços
     */
    // Criando a conexão com o banco de dados.
    // Todo form que criar e quiser conectar com o sql farei o mesmo processo
    // Crio uma conexão nula no momento
    Connection conexao = null;
    // Manipulando instruções sql com PreparedStatement e ResultSet
    PreparedStatement pst = null;
    ResultSet rs = null;
    // Salvar o tipo do os em uma váriavel para salvar dps no banco de dados
    private String tipoDeServico;

    public cadastroServicos() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnLimpar.setVisible(false);
    }

    private void mostrarClientes() {
        String sql = "SELECT idcliente AS Id, nomecliente AS Nome, fonecliente AS Telefone FROM clientes WHERE nomecliente like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // Passando o conteudo da caixinha de pesquisa para o ?
            pst.setString(1, txtBuscarCliente.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a biblioteca para preencher a tabela!
            tblClienteServico.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setarCampos() {
        int setar = tblClienteServico.getSelectedRow();
        txtIdClienteServico.setText(tblClienteServico.getModel().getValueAt(setar, 0).toString());

    }

    private void inserirCampos() {
        int inserir = tblClienteServico.getSelectedRow();

        // Setando o ID dentro da tabela cliente de acordo com o click do usuário no cliente
        txtIdClienteServico.setText(tblClienteServico.getModel().getValueAt(inserir, 0).toString());

    }

    private void adicionarServico() {
        // Cadastra uma ordem de servico no banco de dados
        String sql = "INSERT INTO os (stats,tipo,mecanismo,defeito,servico,funcionario,preco,idcliente) VALUES (?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, ComboStatusServico.getSelectedItem().toString());
            pst.setString(2, tipoDeServico);
            pst.setString(3, txtMecanismoServico.getText());
            pst.setString(4, txtDefeitoServico.getText());
            pst.setString(5, txtServicoServico.getText());
            pst.setString(6, txtFuncionarioServico.getText());
            pst.setString(7, txtPrecoServico.getText().replace(",", "."));
            pst.setString(8, txtIdClienteServico.getText());

            // Validar campos obrigatórios
            if (!txtDefeitoServico.getText().equals("") && !txtMecanismoServico.getText().equals("") && !txtIdClienteServico.getText().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampo();
                    limparTudo();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios corretamente!", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limparCampo() {
        txtBuscarCliente.setText("");
        txtFuncionarioServico.setText("");
        txtDefeitoServico.setText("");
        txtMecanismoServico.setText("");
        txtServicoServico.setText("");
        txtPrecoServico.setText("");
        txtIdServico.setText("");
        txtDataServico.setText("");
    }

    private void pesquisar_os() {
        String numeroOs = JOptionPane.showInputDialog("Digite o número do serviço: ");
        String sql = "SELECT * FROM os WHERE os= " + numeroOs;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            // Caso tenha um registro, preenche o formulário
            if (rs.next()) {
                // vai no banco de dados e pega o valor do ID dele.
                txtIdServico.setText(rs.getString(1));
                txtDataServico.setText(rs.getString(2));
                String tipodeservico = rs.getString(3);
                if (tipoDeServico.equals("Orçamento")) {
                    radioOrcamento.setSelected(true);
                    tipoDeServico = "Orçamento";
                } else {
                    radioOS.setSelected(true);
                    tipoDeServico = "Ordem de serviço";
                }
                ComboStatusServico.setSelectedItem(rs.getString(4));
                txtMecanismoServico.setText(rs.getString(5));
                txtDefeitoServico.setText(rs.getString(6));
                txtServicoServico.setText(rs.getString(7));
                txtFuncionarioServico.setText(rs.getString(8));
                txtPrecoServico.setText(rs.getString(9));
                txtIdClienteServico.setText(rs.getString(10));

                // Enquanto eu busco, essas funcoes ficam indisponíveis
                btnCriar.setEnabled(false);
                txtBuscarCliente.setEnabled(false);
                tblClienteServico.setVisible(false);
                btnLimpar.setVisible(true);
                btnLimpar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Esse serviço não consta no sistema! Tente novamente com outro número de serviço.");
            }

        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Só são aceitos números como identificadores de serviço!", "ERRO", JOptionPane.ERROR_MESSAGE);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }
    }

    private void limparTudo() {
        btnCriar.setEnabled(true);
        tblClienteServico.setVisible(true);
        txtBuscarCliente.setEnabled(true);
        btnLimpar.setEnabled(false);
        txtBuscarCliente.setText("");
        txtFuncionarioServico.setText("");
        txtDefeitoServico.setText("");
        txtMecanismoServico.setText("");
        txtServicoServico.setText("");
        txtPrecoServico.setText("");
        txtIdServico.setText("");
        txtDataServico.setText("");
        txtIdClienteServico.setText("");
    }

    private void editar() {
        String sql = "UPDATE os SET stats=?,tipo=?,mecanismo=?,defeito=?,servico=?,funcionario=?,preco=? WHERE os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, ComboStatusServico.getSelectedItem().toString());
            pst.setString(2, tipoDeServico);
            pst.setString(3, txtMecanismoServico.getText());
            pst.setString(4, txtDefeitoServico.getText());
            pst.setString(5, txtServicoServico.getText());
            pst.setString(6, txtFuncionarioServico.getText());
            pst.setString(7, txtPrecoServico.getText().replace(",", "."));
            pst.setString(8, txtIdServico.getText());
            // Validar campos obrigatórios
            if (!txtDefeitoServico.getText().equals("") && !txtMecanismoServico.getText().equals("") && !txtIdClienteServico.getText().equals("")) {
                int adicionar = pst.executeUpdate();
                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Serviço editado com sucesso !", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampo();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios corretamente para editar!", "Atenção", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esse serviço?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM os WHERE os=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdServico.getText());
                int apagar = pst.executeUpdate();
                if (apagar > 0) {
                    JOptionPane.showMessageDialog(null, "Serviço apagado com sucesso!");
                    limparCampo();
                }else if (apagar == 0)  {
                    JOptionPane.showMessageDialog(null, "Serviço não encontrado.", "ATENÇÃO", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdServico = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDataServico = new javax.swing.JTextField();
        radioOrcamento = new javax.swing.JRadioButton();
        radioOS = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ComboStatusServico = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClienteServico = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtIdClienteServico = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDefeitoServico = new javax.swing.JTextField();
        txtMecanismoServico = new javax.swing.JTextField();
        txtFuncionarioServico = new javax.swing.JTextField();
        txtServicoServico = new javax.swing.JTextField();
        btnCriar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtPrecoServico = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastrar serviços");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(229, 228, 228));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-serviço-de-carro-20.png"))); // NOI18N
        jLabel2.setText("ID SERVIÇO");

        txtIdServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdServico.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdServico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtIdServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtIdServico.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-calendário-20 (1).png"))); // NOI18N
        jLabel3.setText("DATA");

        txtDataServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataServico.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataServico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtDataServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtDataServico.setEnabled(false);

        buttonGroup1.add(radioOrcamento);
        radioOrcamento.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        radioOrcamento.setText("ORÇAMENTO");
        radioOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioOS);
        radioOS.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        radioOS.setText("ORDEM DE SERVIÇO");
        radioOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(radioOrcamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radioOS)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtIdServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(12, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(61, 61, 61))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdServico, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioOrcamento)
                    .addComponent(radioOS)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-serviço-de-carro-32.png"))); // NOI18N
        jLabel1.setText("CADASTRAR SERVIÇOS");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("STATUS");

        ComboStatusServico.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ComboStatusServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lanternagem", "Funilaria", "Pintura", "Finalizando", "Aguardando retirada" }));
        ComboStatusServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtBuscarCliente.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtBuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-cliente-de-pesquisa-40.png"))); // NOI18N

        tblClienteServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblClienteServico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "NOME", "TELEFONE"
            }
        ));
        tblClienteServico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteServicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClienteServico);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("*ID");

        txtIdClienteServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdClienteServico.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdClienteServico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtIdClienteServico.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdClienteServico)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtIdClienteServico, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("* Materiais");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("* Defeito");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Serviço");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Funcionário");

        btnCriar.setBackground(new java.awt.Color(0, 153, 0));
        btnCriar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCriar.setForeground(java.awt.Color.white);
        btnCriar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-adicionar-propriedade-20.png"))); // NOI18N
        btnCriar.setText(" CRIAR");
        btnCriar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(255, 102, 255));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBuscar.setForeground(java.awt.Color.white);
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-pesquisar-mais-22.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(204, 153, 0));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEditar.setForeground(java.awt.Color.white);
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-editar-20.png"))); // NOI18N
        btnEditar.setText(" EDITAR");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRemover.setBackground(new java.awt.Color(255, 51, 51));
        btnRemover.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRemover.setForeground(java.awt.Color.white);
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-apagar-para-sempre-22.png"))); // NOI18N
        btnRemover.setText(" REMOVER");
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Preço");

        txtPrecoServico.setText("0");

        jLabel12.setText("Campos Obrigatórios *");

        btnLimpar.setBackground(new java.awt.Color(204, 255, 255));
        btnLimpar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/caiohenrique/comtainerr/icons/icons8-à-esquerda-dentro-de-um-círculo-15.png"))); // NOI18N
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ComboStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(btnLimpar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(71, 71, 71))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtFuncionarioServico, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPrecoServico, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                                    .addComponent(txtServicoServico)
                                    .addComponent(txtMecanismoServico)
                                    .addComponent(txtDefeitoServico))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCriar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(ComboStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLimpar))
                    .addComponent(jLabel12))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDefeitoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMecanismoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtServicoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtFuncionarioServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtPrecoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCriar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        mostrarClientes();
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void tblClienteServicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteServicoMouseClicked
        // Inserindo o campo de ID com o click do usuario
        inserirCampos();
    }//GEN-LAST:event_tblClienteServicoMouseClicked

    private void radioOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOrcamentoActionPerformed
        // Se o radiobuttonOrcamento for selecionado, ele atribui um texto para a variavel
        tipoDeServico = "Orçamento";
    }//GEN-LAST:event_radioOrcamentoActionPerformed

    private void radioOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOSActionPerformed
        // Se o radiobuttonOrcamento for selecionado, ele atribui um texto para a variavel
        tipoDeServico = "Ordem de serviço";
    }//GEN-LAST:event_radioOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao abrir o form dentro do desktoppane, ele já marca alguma caixinha de radiobutton, para que manipule o usuario a escolher alguma durante o cadastro de servicos =>
        radioOrcamento.setSelected(true);
        tipoDeServico = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarActionPerformed
        adicionarServico();
    }//GEN-LAST:event_btnCriarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        pesquisar_os();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparTudo();
        btnLimpar.setVisible(false);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        excluir();
    }//GEN-LAST:event_btnRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboStatusServico;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCriar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemover;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioOS;
    private javax.swing.JRadioButton radioOrcamento;
    private javax.swing.JTable tblClienteServico;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtDataServico;
    private javax.swing.JTextField txtDefeitoServico;
    private javax.swing.JTextField txtFuncionarioServico;
    private javax.swing.JTextField txtIdClienteServico;
    private javax.swing.JTextField txtIdServico;
    private javax.swing.JTextField txtMecanismoServico;
    private javax.swing.JTextField txtPrecoServico;
    private javax.swing.JTextField txtServicoServico;
    // End of variables declaration//GEN-END:variables
}
