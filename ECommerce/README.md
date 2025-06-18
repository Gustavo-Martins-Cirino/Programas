
# 🛒 Projeto de E-Commerce em Java

Este projeto é um **treino prático** desenvolvido em Java, simulando um sistema básico de e-commerce com funcionalidades como cadastro de produtos e clientes, controle de estoque, carrinho de compras e integração com banco de dados MySQL. O projeto possui duas versões:

- ✅ **Versão console interativa**
- 🧪 **Versão com interface gráfica (GUI - em desenvolvimento)**

---

## 🚀 Funcionalidades

- Cadastro, consulta, atualização e exclusão de produtos
- Cadastro, consulta e exclusão de clientes
- Adição e remoção de itens no carrinho
- Controle de estoque automático
- Verificação de integridade referencial (FKs)
- Interface por linha de comando
- Geração de IDs aleatórios únicos com verificação no banco

---

## 🧰 Tecnologias Utilizadas

| Tecnologia / Ferramenta | Finalidade |
|--------------------------|------------|
| `Java SE 8+`             | Lógica da aplicação |
| `JDBC`                   | Conexão com banco de dados |
| `MySQL`                  | Banco de dados relacional |
| `Scanner (java.util)`    | Interação com o usuário via console |
| `Swing` (GUI)            | Interface gráfica básica (em teste) |

---

## 💡 Competências Exercitadas

- Orientação a Objetos em Java (encapsulamento, construtores, toString, etc.)
- Manipulação de banco de dados com JDBC e SQL
- Uso de `PreparedStatement`, `ResultSet`, `try-with-resources`
- Controle de transações com `commit()` e `rollback()`
- Tratamento de exceções (`SQLException`, `InputMismatchException`)
- Geração e verificação de IDs únicos
- Organização de menus e lógica de navegação por console
- Implementação básica de GUI com `JFrame`, `JTextField`, `JButton`, etc.

---

## 🖼️ Prints (opcional)

Você pode adicionar aqui prints do console em execução e da interface gráfica (se desejar):
```
📦 Produto cadastrado com sucesso no banco de dados!
🛒 Produto 10001 adicionado ao carrinho do cliente 20002.
```

---

## 🧪 Como Executar

1. Certifique-se de ter o **Java JDK** instalado (versão 8 ou superior)
2. Crie o banco de dados `ecommerce` no MySQL com as tabelas adequadas (produto, cliente, carrinho)
3. Ajuste os dados de conexão (`URL`, `USER`, `PASSWORD`) na classe `Conexao`
4. Compile e execute:
```bash
javac ECommerce.java
java ECommerce
```

---

## ⚠️ Observações

- Esse é um projeto de treino, com código concentrado em poucos arquivos propositalmente.
- A versão GUI está em construção e foi usada como experimento inicial com Swing.

---

## 📁 Estrutura do Projeto

```
📦ECommerce
 ┣ 📄ECommerce.java         # Versão console funcional
 ┣ 📄ECommerceGUI.java      # Versão experimental com interface gráfica
```

---

## ✍️ Autor

**Gustavo Martins Cirino**  
Estudante de Desenvolvimento de Sistemas • Java & Banco de Dados  
[GitHub Profile] https://github.com/Gustavo-Martins-Cirino 

---

## 📌 Licença

Este projeto é livre para uso educacional e pessoal. Sinta-se à vontade para estudar, adaptar e compartilhar.
