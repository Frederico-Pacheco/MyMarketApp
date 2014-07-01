package br.com.mymarket.infra;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import br.com.mymarket.R;
import br.com.mymarket.activities.ProdutosActivity;

public class ActionModeCallback implements ActionMode.Callback {

	private ProdutosActivity activity;
	
	public ActionModeCallback(ProdutosActivity activity){
		this.activity = activity;
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// inflate contextual menu
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
				activity.confirmarCompra();
				mode.finish(); // Action picked, so close the CAB
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