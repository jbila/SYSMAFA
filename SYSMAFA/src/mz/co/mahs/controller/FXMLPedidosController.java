package mz.co.mahs.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mz.co.mahs.dao.DaoItemsPedidos;
import mz.co.mahs.dao.DaoParcela;
import mz.co.mahs.dao.DaoPedido;
import mz.co.mahs.models.Cliente;
import mz.co.mahs.models.FormasDePagamento;
import mz.co.mahs.models.ItemsPedidos;
import mz.co.mahs.models.Parcela;
import mz.co.mahs.models.Pedido;
import mz.co.mahs.models.Producto;
import mz.co.mahs.models.Utilizador;

public class FXMLPedidosController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	@FXML
	private AnchorPane rootPedidos;
	@FXML
	private TableView<Pedido> tblPedidos = new TableView<Pedido>();

	@FXML
	private TableColumn<Pedido, Integer> colId;
	@FXML
	private TableColumn<Pedido, Cliente> colCliente;

	@FXML
	private TableColumn<Pedido, String> colTipo;

	@FXML
	private TableColumn<Pedido, Double> colTotal;

	@FXML
	private TableColumn<Pedido, Double> colPago;

	@FXML
	private TableColumn<Pedido, FormasDePagamento> colPagoVia;

	@FXML
	private TableColumn<Pedido, String> colData;

	@FXML
	private TableColumn<Pedido, Utilizador> colUtilizador;
	/** tblItems do pedido */
	@FXML
	private TableView<ItemsPedidos> tblItems;

	@FXML
	private TableColumn<ItemsPedidos, Integer> colIdItems;

	@FXML
	private TableColumn<ItemsPedidos, Producto> colProductoItems;

	@FXML
	private TableColumn<ItemsPedidos, Integer> colQtyItems;
	@FXML
	private TableColumn<ItemsPedidos, Double> colPreco;

	@FXML
	private Button btnPagarParcela,btnLiquidar;

	@FXML
	private Button btnVerItemDoPedido;

	/**----------------------------------*/
	@FXML
    private TableView<Parcela> tblParcelas=new TableView<Parcela>();

    @FXML
    private TableColumn<Parcela, Double> colParcelaValor;

    @FXML
    private TableColumn<Parcela, String> colParcelaPrevisao;

    @FXML
    private TableColumn<Parcela, String> colParcelaDataPagamento;

    @FXML
    private TableColumn<Parcela, String> colParcelaEstado;
    /**-------------------------------------------*/
	@FXML
	private TextField txtProcurar;

	@FXML
	private TextField txtPedido,txtNumeroParcela;

	@FXML
	private void handleMouseClick(MouseEvent event) {

	}

	@FXML
	private void procurador(KeyEvent event) {

	}
	@FXML
	private void liquidar(KeyEvent event) {
		try {
		Parcela parcela=new Parcela();
		parcela.setEstado("PAID");
		parcela.setIdParcela(15);
		DaoParcela.updateParcela(parcela);
		//btnLiquidar.setVisible(false);
		//tblParcelas.setVisible(false);
		}
		catch(java.lang.IllegalArgumentException ex) {
			ex.toString();
		}

	}
	@FXML
	private void verDividas(ActionEvent event) {
		btnLiquidar.setVisible(true);
		tblParcelas.setVisible(true);
		try {
		List<Parcela> list = DaoParcela.getAll(Integer.parseInt(txtPedido.getText()));
		final ObservableList<Parcela> obsList = FXCollections.observableArrayList(list);
		colParcelaValor.setCellValueFactory(new PropertyValueFactory<>("valorApagar"));
		colParcelaPrevisao.setCellValueFactory(new PropertyValueFactory<>("dataPrevista"));
		colParcelaDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
		colParcelaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		tblParcelas.setItems(obsList);
		//System.out.println(list);
		}
		catch(NullPointerException e) {
			e.toString();
		}
	}
	@FXML
    private void handleClik(MouseEvent event) {
		Pedido pedido = tblPedidos.getSelectionModel().getSelectedItem();
		txtPedido.setText("" + pedido.getIdPedido());
		tblItems.setVisible(false);
		tblParcelas.setVisible(false);	
		/*------------------------------*/
		btnPagarParcela.setVisible(true);
		btnVerItemDoPedido.setVisible(true);
    }

	@FXML
    private void handleClikParcela(MouseEvent event) {
		Parcela parcela = tblParcelas.getSelectionModel().getSelectedItem();
		txtNumeroParcela.setText("" + parcela.getIdParcela());
		btnLiquidar.setVisible(false);

		
    }
	@FXML
	private void verItems(ActionEvent event) {
		tblItems.setVisible(true);
		List<ItemsPedidos> list = DaoItemsPedidos.getAll(Integer.parseInt(txtPedido.getText()));
		final ObservableList<ItemsPedidos> obsList = FXCollections.observableArrayList(list);
		colIdItems.setCellValueFactory(new PropertyValueFactory<>("idItemsPedido"));
		colQtyItems.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		colProductoItems.setCellValueFactory(new PropertyValueFactory<>("producto"));
		colPreco.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
		tblItems.setItems(obsList);
	}

	@FXML
	private void initialize() {
		showInfo();
		tblItems.setVisible(false);
		tblParcelas.setVisible(false);
		txtNumeroParcela.setVisible(false);
		
		btnPagarParcela.setVisible(false);
		btnVerItemDoPedido.setVisible(false);
		btnLiquidar.setVisible(false);
	}

	private void showInfo() {
		List<Pedido> list = DaoPedido.getAll();
		final ObservableList<Pedido> obsList = FXCollections.observableArrayList(list);
		colId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
		colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("valorPedido"));
		colPago.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
		colPagoVia.setCellValueFactory(new PropertyValueFactory<>("formasDepagamento"));
		colData.setCellValueFactory(new PropertyValueFactory<>("dataRegisto"));
		colUtilizador.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
		tblPedidos.setItems(obsList);
	}

}
