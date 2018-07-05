/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

/**
 *
 * @author Javier
 */
public class ventanaChatGlobal extends javax.swing.JFrame {
    Cliente usuario;
    DefaultListModel listaUsuarios;
    /**
     * Creates new form ventanaChatGlobal
     */
    public ventanaChatGlobal() {
        initComponents();
        listaUsuarios= new DefaultListModel();
        usuario = new Cliente( listaUsuarios);
        listaConectados.setModel(listaUsuarios);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonRegistrar = new javax.swing.JButton();
        nombreUsuario = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaConectados = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.FlowLayout());

        botonRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/registrar.gif"))); // NOI18N
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(botonRegistrar);
        getContentPane().add(nombreUsuario);

        listaConectados.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaConectados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaConectadosMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(listaConectados);

        getContentPane().add(jScrollPane2);

        jLabel1.setText("Nombre:");
        getContentPane().add(jLabel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        // TODO add your handling code here:
        usuario.setNombre(nombreUsuario.getText());
        int estado= usuario.conectarServidor();
        
        if(estado > 0){
               botonRegistrar.setEnabled(false);
               nombreUsuario.setEnabled(false);
        }else{
        switch(estado){
                case -1:
                    JOptionPane.showMessageDialog(this, "No se ha podido "
                            + "conectar con el servidor,\n ","Error de conexión"
                            , JOptionPane.ERROR_MESSAGE);
                break;
                case -2:
                    JOptionPane.showMessageDialog(this, "Nombre ocupado elige otro"
                            ,"Error Nombre"
                            + "Nombre repetido", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private void listaConectadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaConectadosMousePressed
        // TODO add your handling code here:
        int num = listaConectados.getSelectedIndex();
        if (num >= 0){
            String nuevoUsuario = usuario.getUsuarios().get(num);
            usuario.ConectarConUsuario(nuevoUsuario);
        }
    }//GEN-LAST:event_listaConectadosMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        usuario.desconectarServidor();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    /*
    private void ventanaChatGlobalWindowClosing(java.awt.event.WindowEvent evt) {                                        
        System.exit(0);
    } */
    
    
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
            java.util.logging.Logger.getLogger(ventanaChatGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaChatGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaChatGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaChatGlobal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
               new ventanaChatGlobal().setVisible(true);

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() {
//                new ventanaChatGlobal().setVisible(true);
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listaConectados;
    private javax.swing.JTextField nombreUsuario;
    // End of variables declaration//GEN-END:variables
}
