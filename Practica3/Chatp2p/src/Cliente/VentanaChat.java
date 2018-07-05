package Cliente;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;


public class VentanaChat extends javax.swing.JFrame {
    Cliente c;
    boolean conexionActiva=false;
    DefaultListModel listaUsuariosModelo=new DefaultListModel();
    
    public VentanaChat() {
        initComponents();
        this.setSize(500, 500);
        listaUsuarios.setEnabled(false);
        c=new Cliente(listaUsuariosModelo);
        listaUsuarios.setModel(listaUsuariosModelo);
    }



    private void conexionInicial(){
        int conexion=-1;
        conexion= c.conectarS();
          if (conexion==-2)
            JOptionPane.showMessageDialog(this, "Error de conexion con el servidor", "Error de conexion", JOptionPane.ERROR_MESSAGE); // Si no se puede realizar la conexion, generamos un error.

          try {
            this.setTitle(c.getNombre());
        } catch (RemoteException ex) {
            Logger.getLogger(VentanaChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelChat = new javax.swing.JPanel();
        panelEscritura = new javax.swing.JPanel();
        campoTexto = new javax.swing.JTextField();
        botonNombreUsuario = new javax.swing.JButton();
        panelListaUsuarios = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelChat.setLayout(new java.awt.BorderLayout());

        panelEscritura.setLayout(new javax.swing.BoxLayout(panelEscritura, javax.swing.BoxLayout.LINE_AXIS));
        panelEscritura.add(campoTexto);

        botonNombreUsuario.setText("Cambiar Nombre de Usuario");
        botonNombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNombreUsuarioActionPerformed(evt);
            }
        });
        panelEscritura.add(botonNombreUsuario);

        panelChat.add(panelEscritura, java.awt.BorderLayout.PAGE_END);

        listaUsuarios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaUsuariosMousePressed(evt);
            }
        });
        panelListaUsuarios.setViewportView(listaUsuarios);

        panelChat.add(panelListaUsuarios, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelChat, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonNombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNombreUsuarioActionPerformed
 
        c.setNombre(campoTexto.getText());
        campoTexto.setText("");
        try {
            this.setTitle(c.getNombre());
        } catch (RemoteException ex) {
            Logger.getLogger(VentanaChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.conexionInicial();
      
       if(c.conexionActiva){
        listaUsuarios.setEnabled(true);
        botonNombreUsuario.setEnabled(false);
       }
    }//GEN-LAST:event_botonNombreUsuarioActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       if(c.conexionActiva)
           try {
            c.desconectar();
            
        } catch (RemoteException ex) {
            Logger.getLogger(VentanaChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void listaUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaUsuariosMousePressed
        if(listaUsuarios.getSelectedIndex()>=0){
            try {
                if(c.nombre.compareTo(listaUsuarios.getSelectedValue())!=0)
                    c.conectarU(listaUsuarios.getSelectedValue());
            } catch (RemoteException ex) {
                Logger.getLogger(VentanaChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_listaUsuariosMousePressed

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
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonNombreUsuario;
    private javax.swing.JTextField campoTexto;
    private javax.swing.JList<String> listaUsuarios;
    private javax.swing.JPanel panelChat;
    private javax.swing.JPanel panelEscritura;
    private javax.swing.JScrollPane panelListaUsuarios;
    // End of variables declaration//GEN-END:variables
}
