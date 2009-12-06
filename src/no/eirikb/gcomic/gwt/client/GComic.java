/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package no.eirikb.gcomic.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;
import java.util.List;

/**
 * HelloWorld application.
 */
public class GComic implements EntryPoint {

    private List<int[]> frames;
    private int frame;
    private Image image;
    private GWTCanvas canvas;

    public void onModuleLoad() {
        image = new Image();
        image.setVisible(false);
        final TextBox textBox = new TextBox();
        textBox.setText("http://imgs.xkcd.com/comics/prudence.png");
        textBox.setWidth("200px");

        canvas = new GWTCanvas(640, 480);
        canvas.getElement().setId("canvas");

        Button b = new Button("Get image", new ClickHandler() {

            public void onClick(ClickEvent event) {
                image.setUrl("http://www.cmctraining.org/images/loading.gif");
                image.setVisible(true);
                ImageServiceAsync imageServiceAsync = (ImageServiceAsync) GWT.create(ImageService.class);
                ServiceDefTarget endpoint = (ServiceDefTarget) imageServiceAsync;
                endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "ImageService");
                imageServiceAsync.getImage(textBox.getText(), new AsyncCallback<List<int[]>>() {

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.toString());
                    }

                    public void onSuccess(List<int[]> result) {
                        image.setUrl(textBox.getText());
                        frames = result;
                        show(0);
                    }
                });
            }
        });

        Button prev = new Button("Previous", new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (frames != null) {
                    frame--;
                    frame = frame < 0 ? frames.size() - 1 : frame;
                    show();
                }
            }
        });

        Button next = new Button("Next", new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (frames != null) {
                    frame++;
                    frame = frame >= frames.size() ? 0 : frame;
                    show();
                }
            }
        });

        VerticalPanel vp1 = new VerticalPanel();

        HorizontalPanel hp1 = new HorizontalPanel();
        hp1.add(textBox);
        hp1.add(b);
        vp1.add(hp1);

        vp1.add(image);

        HorizontalPanel hp2 = new HorizontalPanel();
        hp2.add(prev);
        hp2.add(next);
        vp1.add(hp2);



        vp1.add(new Button("Test", new ClickHandler() {

            public void onClick(ClickEvent event) {
                ImageLoader.loadImages(new String[]{"http://imgs.xkcd.com/comics/prudence.png"}, new ImageLoader.CallBack() {

                    public void onImagesLoaded(ImageElement[] imageElements) {
                        //canvas.drawImage(imageElements[0], 0, 0);

                        Window.alert("1: " + getPixel());
                    }
                });
            }
        }));
        vp1.add(canvas);
        RootPanel.get().add(vp1);
    }

    native String getPixel() /*-{
    var img = new Image();
    img.src = 'test.png';
    var context = $doc.getElementById('canvas').getContext('2d');
    context.drawImage(img, 0, 0);
    var data;
    try {
    try {
    data = context.getImageData(sx, sy, sw, sh).data;
    } catch (e) {
    netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
    data = context.getImageData(sx, sy, sw, sh).data;
    }
    } catch (e) {
    throw new Error("unable to access image data: " + e);
    }
    
    return "hah";
    }-*/;

    private void show(int frame) {
        this.frame = frame;
        show();
    }

    private void show() {
        image.setVisible(true);
        int minX = frames.get(frame)[0];
        int maxX = frames.get(frame)[1];
        int minY = frames.get(frame)[2];
        int maxY = frames.get(frame)[3];
        image.setVisibleRect(minX, minY, maxX - minX, maxY - minY);
    }
}
