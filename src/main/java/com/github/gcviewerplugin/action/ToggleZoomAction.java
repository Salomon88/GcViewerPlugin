package com.github.gcviewerplugin.action;

import com.github.gcviewerplugin.GCDocumentWrapper;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ToggleAction;
import org.jetbrains.annotations.NotNull;

import static com.intellij.icons.AllIcons.General.ZoomIn;

public class ToggleZoomAction extends ToggleAction {

    private  final GCDocumentWrapper gcDocWrapper;

    public ToggleZoomAction(GCDocumentWrapper gcDocWrapper, ZoomPercent zoomPercent) {
       super(zoomPercent.getValue());
       this.gcDocWrapper = gcDocWrapper;
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return gcDocWrapper.getModelChart().getScaleFactor() == getScale(e);
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        gcDocWrapper.getModelChart().setScaleFactor(getScale(e));
    }

    private double getScale(@NotNull AnActionEvent e) {
        String rawScale = e.getPresentation().getText();
        System.out.println("rawScale "+rawScale);
        return Double.valueOf(rawScale.substring(0,rawScale.length()-1));
    }

 public static class ZoomActionGroup extends DefaultActionGroup {
      public ZoomActionGroup(GCDocumentWrapper gcDocumentWrapper) {
          super("Zoom",true);
          getTemplatePresentation().setIcon(ZoomIn);
          for (ZoomPercent percent : ZoomPercent.values()) {
              add(new ToggleZoomAction(gcDocumentWrapper, percent));
          }
      }
 }

    private enum ZoomPercent {
        Z1("1%"),
        Z5("5%"),
        Z10("10%"),
        Z50("50%"),
        Z100("100%"),
        Z200("200%"),
        Z300("300%"),
        Z500("500%"),
        Z1000("1000%"),
        Z5000("5000%");

        final String value;
        ZoomPercent(String s) {
          this.value = s;
        }
        public String getValue() {
            return value;
        }
    }
}

