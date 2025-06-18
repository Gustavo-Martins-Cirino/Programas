package Projetos.Treinos.Ecommerce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ECommerceGUI extends JFrame {
    // --- Componentes Produtos ---
    private JTextField idProdutoField;
    private JTextField nomeProdutoField;
    private JTextField descricaoProdutoField;
    private JTextField precoProdutoField;
    private JTextField quantidadeProdutoField;
    private JTextField corProdutoField;
    private JTextField categoriaProdutoField;
    private JTextArea resultadoProdutoArea;

    // --- Componentes Clientes ---
    private JTextField idClienteField;
    private JTextField nomeClienteField;
    private JTextArea resultadoClienteArea;

    // --- Componentes Carrinho ---
    private JTextField idClienteCarrinhoField;
    private JTextField idProdutoCarrinhoField;
    private JTextField quantidadeCarrinhoField;
    private JTextArea resultadoCarrinhoArea;

    public ECommerceGUI() {
        // Configurações da janela principal
        setTitle("Sistema de E-commerce");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Failed to set system look and feel: " + ex);
            }
        }


        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Aba de Produtos ---
        JPanel produtoPanel = criarPainelProduto();
        tabbedPane.addTab("Produtos", produtoPanel);

        // --- Aba de Clientes ---
        JPanel clientePanel = criarPainelCliente();
        tabbedPane.addTab("Clientes", clientePanel);

        // --- Aba do Carrinho ---
        JPanel carrinhoPanel = criarPainelCarrinho();
        tabbedPane.addTab("Carrinho de Compras", carrinhoPanel);
        add(tabbedPane);
    }



    private JPanel criarPainelProduto() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));


        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Dados do Produto", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        int row = 0;
        row = addFormField(inputPanel, gbc, row, "ID do Produto (0 para auto-gerar, ou para consultar/atualizar/remover):", idProdutoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "Nome do Produto:", nomeProdutoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "Descrição:", descricaoProdutoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "Preço:", precoProdutoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "Quantidade:", quantidadeProdutoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "Cor:", corProdutoField = new JTextField());
        addFormField(inputPanel, gbc, row, "Categoria:", categoriaProdutoField = new JTextField());

        panel.add(inputPanel, BorderLayout.NORTH);

        // Botões:
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        JButton consultarProdutoButton = new JButton("Consultar Produto por ID");
        JButton atualizarProdutoButton = new JButton("Atualizar Produto");
        JButton removerProdutoButton = new JButton("Remover Produto");
        JButton listarProdutosButton = new JButton("Listar Todos os Produtos");

        buttonPanel.add(cadastrarProdutoButton);
        buttonPanel.add(consultarProdutoButton);
        buttonPanel.add(atualizarProdutoButton);
        buttonPanel.add(removerProdutoButton);
        buttonPanel.add(listarProdutosButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Área do resultado:
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Resultados do Produto", TitledBorder.LEFT, TitledBorder.TOP));
        resultadoProdutoArea = new JTextArea(10, 50);
        resultadoProdutoArea.setEditable(false);
        resultadoProdutoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultadoProdutoArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(resultPanel, BorderLayout.SOUTH);


        cadastrarProdutoButton.addActionListener(e -> cadastrarProduto());
        consultarProdutoButton.addActionListener(e -> consultarProdutoPorIdGUI());
        atualizarProdutoButton.addActionListener(e -> atualizarProdutoGUI());
        removerProdutoButton.addActionListener(e -> removerProdutoGUI());
        listarProdutosButton.addActionListener(e -> listarTodosProdutosGUI());

        return panel;
    }

    private JPanel criarPainelCliente() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));


        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Dados do Cliente", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addFormField(inputPanel, gbc, row, "ID do Cliente (0 para auto-gerar, ou para consultar/atualizar/remover):", idClienteField = new JTextField());
        addFormField(inputPanel, gbc, row, "Nome do Cliente:", nomeClienteField = new JTextField());

        panel.add(inputPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
        JButton consultarClienteButton = new JButton("Consultar Cliente por ID");
        JButton atualizarClienteButton = new JButton("Atualizar Cliente");
        JButton removerClienteButton = new JButton("Remover Cliente");
        JButton listarClientesButton = new JButton("Listar Todos os Clientes");

        buttonPanel.add(cadastrarClienteButton);
        buttonPanel.add(consultarClienteButton);
        buttonPanel.add(atualizarClienteButton);
        buttonPanel.add(removerClienteButton);
        buttonPanel.add(listarClientesButton);

        panel.add(buttonPanel, BorderLayout.CENTER);


        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Resultados do Cliente", TitledBorder.LEFT, TitledBorder.TOP));
        resultadoClienteArea = new JTextArea(10, 50);
        resultadoClienteArea.setEditable(false);
        resultadoClienteArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultadoClienteArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(resultPanel, BorderLayout.SOUTH);


        cadastrarClienteButton.addActionListener(e -> cadastrarCliente());
        consultarClienteButton.addActionListener(e -> consultarClientePorIdGUI());
        atualizarClienteButton.addActionListener(e -> atualizarClienteGUI());
        removerClienteButton.addActionListener(e -> removerClienteGUI());
        listarClientesButton.addActionListener(e -> listarTodosClientesGUI());

        return panel;
    }

    private JPanel criarPainelCarrinho() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));


        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Gerenciamento de Carrinho", TitledBorder.LEFT, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addFormField(inputPanel, gbc, row, "ID do Cliente:", idClienteCarrinhoField = new JTextField());
        row = addFormField(inputPanel, gbc, row, "ID do Produto:", idProdutoCarrinhoField = new JTextField());
        addFormField(inputPanel, gbc, row, "Quantidade:", quantidadeCarrinhoField = new JTextField());

        panel.add(inputPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton adicionarItemButton = new JButton("Adicionar Item ao Carrinho");
        JButton removerItemButton = new JButton("Remover Item do Carrinho");
        JButton listarCarrinhoButton = new JButton("Listar Carrinho do Cliente");

        buttonPanel.add(adicionarItemButton);
        buttonPanel.add(removerItemButton);
        buttonPanel.add(listarCarrinhoButton);

        panel.add(buttonPanel, BorderLayout.CENTER);


        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Resultados do Carrinho", TitledBorder.LEFT, TitledBorder.TOP));
        resultadoCarrinhoArea = new JTextArea(10, 50);
        resultadoCarrinhoArea.setEditable(false);
        resultadoCarrinhoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultadoCarrinhoArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(resultPanel, BorderLayout.SOUTH);


        adicionarItemButton.addActionListener(e -> adicionarItemAoCarrinhoGUI());
        removerItemButton.addActionListener(e -> removerItemDoCarrinhoGUI());
        listarCarrinhoButton.addActionListener(e -> listarItensCarrinhoGUI());

        return panel;
    }

    private int addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        panel.add(textField, gbc);
        return row + 1;
    }


    private void cadastrarProduto() {
        try {
            String nome = nomeProdutoField.getText();
            String descricao = descricaoProdutoField.getText();
            double preco = Double.parseDouble(precoProdutoField.getText());
            int quantidade = Integer.parseInt(quantidadeProdutoField.getText());
            String cor = corProdutoField.getText();
            String categoria = categoriaProdutoField.getText();
            Long idProduto = Long.parseLong(idProdutoField.getText());

            if (nome.isEmpty() || descricao.isEmpty() || cor.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos de texto.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (preco < 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(this, "Preço e quantidade não podem ser negativos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idProduto == 0L) {
                boolean idJaExiste;
                do {
                    idProduto = ECommerce.gerarIdAleatorioComLimite(5);
                    idJaExiste = (ECommerce.consultarProdutoPorId(idProduto) != null);
                } while (idJaExiste);
                JOptionPane.showMessageDialog(this, "ID do produto gerado: " + idProduto, "ID Gerado", JOptionPane.INFORMATION_MESSAGE);
            } else if (ECommerce.consultarProdutoPorId(idProduto) != null) {
                JOptionPane.showMessageDialog(this, "ID do produto " + idProduto + " já existe. Por favor, escolha outro ou digite 0 para gerar um.", "Erro de ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ECommerce.Produto novoProduto = new ECommerce.Produto(nome, descricao, preco, quantidade, cor, categoria, idProduto);
            ECommerce.inserirProduto(novoProduto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposProduto();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para Preço, Quantidade e ID.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarProdutoPorIdGUI() {
        try {
            Long idParaConsultar = Long.parseLong(idProdutoField.getText());
            ECommerce.Produto produtoEncontrado = ECommerce.consultarProdutoPorId(idParaConsultar);
            if (produtoEncontrado != null) {
                resultadoProdutoArea.setText(produtoEncontrado.toString());
                // Preenche os campos com os dados do produto encontrado
                nomeProdutoField.setText(produtoEncontrado.getNome());
                descricaoProdutoField.setText(produtoEncontrado.getDescricao());
                precoProdutoField.setText(String.valueOf(produtoEncontrado.getPreco()));
                quantidadeProdutoField.setText(String.valueOf(produtoEncontrado.getQuantidade()));
                corProdutoField.setText(produtoEncontrado.getCor());
                categoriaProdutoField.setText(produtoEncontrado.getCategoria());
            } else {
                resultadoProdutoArea.setText("Produto com ID " + idParaConsultar + " não encontrado.");
                limparCamposProduto();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido para consultar.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            resultadoProdutoArea.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarProdutoGUI() {
        try {
            Long idParaAtualizar = Long.parseLong(idProdutoField.getText());
            String nome = nomeProdutoField.getText();
            String descricao = descricaoProdutoField.getText();
            double preco = Double.parseDouble(precoProdutoField.getText());
            int quantidade = Integer.parseInt(quantidadeProdutoField.getText());
            String cor = corProdutoField.getText();
            String categoria = categoriaProdutoField.getText();

            if (idParaAtualizar == 0L) {
                JOptionPane.showMessageDialog(this, "Para atualizar, o ID do produto deve ser válido e diferente de 0.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nome.isEmpty() || descricao.isEmpty() || cor.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para atualização.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (preco < 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(this, "Preço e quantidade não podem ser negativos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ECommerce.Produto produtoAtualizado = new ECommerce.Produto(nome, descricao, preco, quantidade, cor, categoria, idParaAtualizar);
            ECommerce.atualizarProduto(produtoAtualizado);
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposProduto();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para Preço, Quantidade e ID.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProdutoGUI() {
        try {
            Long idParaRemover = Long.parseLong(idProdutoField.getText());
            if (idParaRemover == 0L) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um ID de produto válido para remover.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o produto com ID: " + idParaRemover + "?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ECommerce.excluirProduto(idParaRemover);
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposProduto();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido para remover.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodosProdutosGUI() {
        ArrayList<ECommerce.Produto> todosProdutos = ECommerce.consultarTodosProdutos();
        if (todosProdutos.isEmpty()) {
            resultadoProdutoArea.setText("Nenhum produto cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder("--- Lista de Produtos ---\n");
            for (ECommerce.Produto p : todosProdutos) {
                sb.append(p.toString()).append("\n");
            }
            sb.append("-------------------------");
            resultadoProdutoArea.setText(sb.toString());
        }
    }

    private void limparCamposProduto() {
        idProdutoField.setText("");
        nomeProdutoField.setText("");
        descricaoProdutoField.setText("");
        precoProdutoField.setText("");
        quantidadeProdutoField.setText("");
        corProdutoField.setText("");
        categoriaProdutoField.setText("");
    }

    // --- Métodos de Cliente ---
    private void cadastrarCliente() {
        try {
            String nome = nomeClienteField.getText();
            Long idCliente = Long.parseLong(idClienteField.getText());

            if (idCliente == 0L) {
                boolean idJaExiste;
                do {
                    idCliente = ECommerce.gerarIdAleatorioComLimite(5);
                    idJaExiste = (ECommerce.consultarClientePorId(idCliente) != null);
                } while (idJaExiste);
                JOptionPane.showMessageDialog(this, "ID do cliente gerado: " + idCliente, "ID Gerado", JOptionPane.INFORMATION_MESSAGE);
            } else if (ECommerce.consultarClientePorId(idCliente) != null) {
                JOptionPane.showMessageDialog(this, "ID do cliente " + idCliente + " já existe. Por favor, escolha outro ou digite 0 para gerar um.", "Erro de ID", JOptionPane.ERROR_MESSAGE);
                return;
            }


            ECommerce.Cliente novoCliente = new ECommerce.Cliente(nome, idCliente);
            ECommerce.inserirCliente(novoCliente);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposCliente();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarClientePorIdGUI() {
        try {
            Long idParaConsultar = Long.parseLong(idClienteField.getText());
            ECommerce.Cliente clienteEncontrado = ECommerce.consultarClientePorId(idParaConsultar);
            if (clienteEncontrado != null) {
                resultadoClienteArea.setText(clienteEncontrado.toString());
                nomeClienteField.setText(clienteEncontrado.getNomeCliente());
            } else {
                resultadoClienteArea.setText("Cliente com ID " + idParaConsultar + " não encontrado.");
                limparCamposCliente();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido para consultar.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            resultadoClienteArea.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarClienteGUI() {
        try {
            Long idParaAtualizar = Long.parseLong(idClienteField.getText());
            String nome = nomeClienteField.getText();

            if (idParaAtualizar == 0L) {
                JOptionPane.showMessageDialog(this, "Para atualizar, o ID do cliente deve ser válido e diferente de 0.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ECommerce.Cliente clienteAtualizado = new ECommerce.Cliente(nome,idParaAtualizar);
            ECommerce.atualizarCliente(clienteAtualizado);
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposCliente();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido para atualizar.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerClienteGUI() {
        try {
            Long idParaRemover = Long.parseLong(idClienteField.getText());
            if (idParaRemover == 0L) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um ID de cliente válido para remover.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o cliente com ID: " + idParaRemover + "?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ECommerce.excluirCliente(idParaRemover);
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposCliente();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID numérico válido para remover.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodosClientesGUI() {
        ArrayList<ECommerce.Cliente> todosClientes = ECommerce.consultarTodosClientes();
        if (todosClientes.isEmpty()) {
            resultadoClienteArea.setText("Nenhum cliente cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder("--- Lista de Clientes ---\n");
            for (ECommerce.Cliente c : todosClientes) {
                sb.append(c.toString()).append("\n");
            }
            sb.append("-------------------------");
            resultadoClienteArea.setText(sb.toString());
        }
    }

    private void limparCamposCliente() {
        idClienteField.setText("");
        nomeClienteField.setText("");
    }

    // --- Métodos de Carrinho ---
    private void adicionarItemAoCarrinhoGUI() {
        try {
            Long idCliente = Long.parseLong(idClienteCarrinhoField.getText());
            Long idProduto = Long.parseLong(idProdutoCarrinhoField.getText());
            int quantidade = Integer.parseInt(quantidadeCarrinhoField.getText());

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser positiva.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }


            ECommerce.adicionarItemAoCarrinho(idCliente, idProduto, quantidade);
            JOptionPane.showMessageDialog(this, "Item adicionado ao carrinho com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCamposCarrinho();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira IDs e quantidade numéricos válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar item ao carrinho: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerItemDoCarrinhoGUI() {
        try {
            Long idCliente = Long.parseLong(idClienteCarrinhoField.getText());
            Long idProduto = Long.parseLong(idProdutoCarrinhoField.getText());
            int quantidade = Integer.parseInt(quantidadeCarrinhoField.getText());

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade a remover deve ser positiva.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover " + quantidade + " unidades do produto " + idProduto + " do carrinho do cliente " + idCliente + "?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ECommerce.removerItemDoCarrinho(idCliente, idProduto, quantidade);
                JOptionPane.showMessageDialog(this, "Item(ns) removido(s) do carrinho com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCamposCarrinho();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira IDs e quantidade numéricos válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover item do carrinho: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarItensCarrinhoGUI() {
        try {
            Long idCliente = Long.parseLong(idClienteCarrinhoField.getText());
            ArrayList<ECommerce.CarrinhoItem> itensCarrinho = ECommerce.listarItensDoCarrinho(idCliente);

            if (itensCarrinho.isEmpty()) {
                resultadoCarrinhoArea.setText("Carrinho do cliente " + idCliente + " está vazio.");
            } else {
                StringBuilder sb = new StringBuilder("--- Itens no Carrinho do Cliente " + idCliente + " ---\n");
                for (ECommerce.CarrinhoItem item : itensCarrinho) {
                    ECommerce.Produto prod = ECommerce.consultarProdutoPorId(item.getIdProduto());
                    String nomeProd = (prod != null) ? prod.getNome() : "Produto Desconhecido";
                    sb.append("  Produto: ").append(nomeProd)
                            .append(" (ID: ").append(item.getIdProduto())
                            .append("), Quantidade: ").append(item.getQuantidade()).append("\n");
                }
                sb.append("--------------------------------------------------");
                resultadoCarrinhoArea.setText(sb.toString());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um ID de cliente numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            resultadoCarrinhoArea.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar carrinho: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCamposCarrinho() {
        idClienteCarrinhoField.setText("");
        idProdutoCarrinhoField.setText("");
        quantidadeCarrinhoField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceGUI().setVisible(true));
    }
}