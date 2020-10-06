package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.StudentInfo;

/**
 * Panel containing the list of student information.
 */
public class StudentInfoListPanel extends UiPart<Region> {

    private static final String FXML = "StudentInfoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentInfoListPanel.class);

    @FXML
    private ListView<StudentInfo> studentInfoListView;

    /**
     * Creates a {@code StudentInfoListPanel} with the given {@code ObservableList}.
     */
    public StudentInfoListPanel(ObservableList<StudentInfo> studentInfoList) {
        super(FXML);
        studentInfoListView.setItems(studentInfoList);
        studentInfoListView.setCellFactory(listView -> new StudentInfoListPanel.StudentInfoListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudentInfo} using a {@code StudentInfoCard}.
     */
    class StudentInfoListViewCell extends ListCell<StudentInfo> {

        @Override
        protected void updateItem(StudentInfo studentInfo, boolean empty) {
            super.updateItem(studentInfo, empty);

            if (empty || studentInfo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentInfoCard(studentInfo, getIndex() + 1).getRoot());
            }
        }
    }

}
