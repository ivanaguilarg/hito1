package es.udc.ipm.aleatorizador.modelo;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class SelectedCategoryEvent {

    private Category category;
    private int index;

    public SelectedCategoryEvent(Category category, int index) {
        this.category = category;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Category getCategory() {
        return category;
    }

}
