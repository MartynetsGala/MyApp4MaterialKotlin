package ru.martynets.myapp4materialkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import ru.martynets.myapp4materialkotlin.R.drawable.*
import ru.martynets.myapp4materialkotlin.R.menu.bottomappbar_menu_primary
import ru.martynets.myapp4materialkotlin.R.menu.bottomappbar_menu_secondary
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottom_app_bar)

        val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onShown(fab: FloatingActionButton?) {
                super.onShown(fab)
            }
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                bottom_app_bar.toggleFabAlignment()
                bottom_app_bar.replaceMenu(
                        if(currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) bottomappbar_menu_secondary
                        else bottomappbar_menu_primary
                )
                fab?.setImageDrawable(
                        if(currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) getDrawable(ic_backup)
                        else getDrawable(ic_add_on_secondary)
                )
                fab?.show()
            }
        }

        toggle_fab_alignment_button.setOnClickListener {
            fab.hide(addVisibilityChanged)
            invalidateOptionsMenu()
            bottom_app_bar.navigationIcon = if (bottom_app_bar.navigationIcon != null) null
            else getDrawable(ic_menu_normal)
            when(screen_label.text) {
                getString(R.string.primary_screen_text) -> screen_label.text = getString(R.string.secondary_screen_text)
                getString(R.string.secondary_screen_text) -> screen_label.text = getString(R.string.primary_screen_text)
            }
        }

        fab.setOnClickListener {
            displayMaterialSnackBar()
        }

    }

    private fun BottomAppBar.toggleFabAlignment() {
        currentFabAlignmentMode = fabAlignmentMode
        fabAlignmentMode = currentFabAlignmentMode.xor(1)
    }

    private fun displayMaterialSnackBar() {
        val marginSide = 0
        val marginBottom = 550
        val snackbar = Snackbar.make(
                coordinatorLayout2,
                "FAB Clicked",
                Snackbar.LENGTH_LONG
        ).setAction("UNDO") {  }
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorSnackbarButton))

        val snackbarView = snackbar.view
        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams

        params.setMargins(
                params.leftMargin + marginSide,
                params.topMargin,
                params.rightMargin + marginSide,
                params.bottomMargin + marginBottom
        )

        snackbarView.layoutParams = params
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottomappbar_menu_primary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }

}

