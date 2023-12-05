import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.text.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.DefaultListCellRenderer;

import java.awt.*;//color

public class SammysAPP extends JFrame{
    
    Rentals rentals = new Rentals();
    int id = 1;
    boolean lesson = false;

    public SammysAPP(){

        setLayout(null);
        setTitle("Ambar Sand");//basico



        /*JLabel icon_label = new JLabel();
        ImageIcon icon = new ImageIcon("surfboard.png");
        icon_label.setIcon(icon);
        add(icon_label);
        icon_label.setBounds(100, 100, 200, 200);*/

        

        //PAINEL RELACIONADO AO TEMPO
        JPanel time_panel = new JPanel(new BorderLayout());
        add(time_panel, BorderLayout.CENTER);
        time_panel.setBackground(new Color(255, 192, 5));
        time_panel.setBounds(200, 100, 400, 35);//posição e tamanho dos elementos

        JLabel time_label = new JLabel("Tempo de aluguel");
        time_label.setPreferredSize(new Dimension(150, 35));
        time_label.setHorizontalAlignment(SwingConstants.CENTER);
        time_label.setVerticalAlignment(SwingConstants.CENTER);
        time_panel.add(time_label, BorderLayout.WEST);

        JTextArea time_area = new JTextArea();
        time_panel.add(time_area, BorderLayout.CENTER);



        //CHECKBOX
        JCheckBox checkBox = new JCheckBox("incluir aula");
        checkBox.setBounds(500, 275, 100, 35);
        checkBox.setBackground(new Color(255, 192, 5));
        add(checkBox);

        checkBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                lesson = true;
            } 
            else if(e.getStateChange() == ItemEvent.DESELECTED){
                lesson = false;
            }
        });



        //PAINEL RELACIONADO AO EQUIPAMENTO
        JPanel equipment_panel = new JPanel(new BorderLayout());
        add(equipment_panel);
        equipment_panel.setBackground(new Color(255, 192, 5));
        equipment_panel.setBounds(200, 200, 400, 35);//posição e tamanho dos elementos

        JLabel equipment_label = new JLabel("Equipamento");
        equipment_label.setPreferredSize(new Dimension(150, 35));
        equipment_label.setHorizontalAlignment(SwingConstants.CENTER);
        equipment_label.setVerticalAlignment(SwingConstants.CENTER);
        equipment_panel.add(equipment_label, BorderLayout.WEST);

        String[] types = {"jet ski", "barco pontão", "barco a remo", "canoa", "caiaque", "cadeira de praia", "guarda-sol", "gazebo"};
        JComboBox<String> comboBox = new JComboBox<>(types);
        equipment_panel.add(comboBox, BorderLayout.CENTER);

        comboBox.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            id = (source.getSelectedIndex() + 1);
            String selectedOption = (String) source.getSelectedItem();

            //se elemento definitivamente sem aula. check box desabilitada
            if( ("cadeira de praia".equals(selectedOption)) || ("guarda-sol".equals(selectedOption)) || ("gazebo".equals(selectedOption)) ){
                checkBox.setSelected(false);//desmarca
                checkBox.setEnabled(false);//desabilita
            } 
            else{
                checkBox.setEnabled(true);//operante
            }
        });



        //aluga ou trata erro
        JButton Confirm_button = new JButton("Confirmar Aluguel");
        Confirm_button.setBounds(230, 350, 140, 35);
        add(Confirm_button);

        Confirm_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    if(time_area.getText().isEmpty()) {
                        throw new NumberFormatException("tempo de aluguel não informado.", "TIME ERROR");
                    } 
                    else if(!time_area.getText().matches("\\d+")) {
                        throw new NumberFormatException("o tempo de aluguel não pode conter letras", "TIME ERROR");
                    } 
                    else if (Integer.parseInt(time_area.getText()) <= 0) {
                        throw new NumberFormatException("o tempo de aluguel não pode ser zero ou negativo", "TIME ERROR");
                    } 
                    else{
                        int time = Integer.parseInt(time_area.getText());

                        String confirmation_str = rentals.newRental(id, time, lesson);
                        JOptionPane.showMessageDialog(null, confirmation_str, "Compra finalizada!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        JButton list_button = new JButton("Listar Aluguéis");
        list_button.setBounds(460, 350, 140, 35);
        add(list_button);

        list_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(rentals.isEmpty()){
                    JOptionPane.showMessageDialog(null, "ainda não há alugueis!", "Lista vazia", JOptionPane.INFORMATION_MESSAGE);
                    rentals.loadFile();
                } 
                else{
                    rentals.saveToFile("null");

                    ArrayList<String> dataList = new ArrayList<>(rentals.listAll2());

                    JDialog listDialog = new JDialog();
                    listDialog.setTitle("Lista de aluguéis");
                    listDialog.setSize(new Dimension(400, 400));
                    listDialog.setLocationRelativeTo(null);
                    listDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                    JList<String> list = new JList<>(dataList.toArray(new String[dataList.size()]));

                    // Defina uma célula de renderização personalizada para quebras de linha
                    list.setCellRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                boolean isSelected, boolean cellHasFocus) {
                            // Adicione quebras de linha entre as informações
                            String[] lines = value.toString().split("\n");

                            StringBuilder html = new StringBuilder("<html>");
                            for (String line : lines) {
                                html.append(line).append("<br>");
                            }
                            html.append("</html>");

                            return super.getListCellRendererComponent(list, html.toString(), index, isSelected,
                                    cellHasFocus);
                        }
                    });

                    JScrollPane scrollPane = new JScrollPane(list);
                    listDialog.add(scrollPane);

                    listDialog.setVisible(true);
                }
            }
        });




        setPreferredSize(new Dimension(854, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 192, 5));
        setLocationRelativeTo(null);
        setVisible(true);
        pack();


    }

        

    public static void main(String[]args){

        new SammysAPP();
    }

}
