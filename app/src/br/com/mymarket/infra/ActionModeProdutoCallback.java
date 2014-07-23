package br.com.mymarket.infra;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import br.com.mymarket.R;
import br.com.mymarket.activities.ProdutosActivity;
import br.com.mymarket.model.Produto;

public class ActionModeProdutoCallback implements ActionMode.Callback {

	
	private List<Produto> listProdutosSelecionados;
	
	private ProdutosActivity activity;
	
	public ActionModeProdutoCallback(ProdutosActivity activity){
		this.activity = activity;
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(R.menu.menu_actionmode, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_send_shop:
				listProdutosSelecionados = new ArrayList<Produto>();
				SparseBooleanArray selected = activity.getAdapter().getSelectedIds();
				for (int i = (selected.size() - 1); i >= 0; i--) {
					if (selected.valueAt(i)) {
						Produto selectedItem = (Produto)activity.getAdapter().getItem(selected.keyAt(i));
						listProdutosSelecionados.add(selectedItem);
					}
				}
				activity.popupConfirmarCompra(listProdutosSelecionados);
				mode.finish();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		activity.getAdapter().removeSelection();
		activity.resetActionMode();
	}
}