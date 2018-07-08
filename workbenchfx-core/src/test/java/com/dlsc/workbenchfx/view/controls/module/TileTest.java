package com.dlsc.workbenchfx.view.controls.module;

import static com.dlsc.workbenchfx.testing.MockFactory.createMockModule;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.module.WorkbenchModule;
import com.dlsc.workbenchfx.testing.MockTile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Test for {@link Tile}.
 */
class TileTest extends ApplicationTest {

  private FxRobot robot;
  private Workbench mockBench;
  private static final int SIZE = 10;
  private WorkbenchModule[] mockModules = new WorkbenchModule[SIZE];
  private Node[] moduleNodes = new Node[SIZE];
  private Node[] moduleIcons = new Node[SIZE];
  private ObservableList<WorkbenchModule> modulesList;

  private MockTile tile;

  @Override
  public void start(Stage stage) {
    robot = new FxRobot();

    mockBench = mock(Workbench.class);

    for (int i = 0; i < moduleNodes.length; i++) {
      moduleNodes[i] = new Label("Module Content");
      moduleIcons[i] = new Label("Module Icon " + i);
    }

    for (int i = 0; i < mockModules.length; i++) {
      mockModules[i] = createMockModule(moduleNodes[i], moduleIcons[i], true, "Module " + i);
    }

    modulesList = FXCollections.observableArrayList(mockModules);
    when(mockBench.getModules()).thenReturn(modulesList);

    tile = new MockTile(mockBench);
    tile.setModule(mockModules[0]);

    Scene scene = new Scene(tile, 100, 100);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void testModuleListener() {
    assertEquals("Module 0", tile.getName());
    assertEquals("Module Icon 0", ((Label)tile.getIcon()).getText());

    // change to module 1
    tile.setModule(mockModules[1]);
    assertEquals("Module 1", tile.getName());
    assertEquals("Module Icon 1", ((Label)tile.getIcon()).getText());
  }

  @Test
  void open() {
    // initial module
    tile.open();
    verify(mockBench).openModule(mockModules[0]);

    // newly set module
    tile.setModule(mockModules[1]);
    tile.open();
    verify(mockBench).openModule(mockModules[1]);
  }
}