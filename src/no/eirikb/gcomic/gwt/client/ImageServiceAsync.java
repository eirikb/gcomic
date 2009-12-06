/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.gcomic.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author eirikb
 */
public interface ImageServiceAsync {

    public void test(String text, AsyncCallback<String> asyncCallback);

    public void getImage(String imageURL, AsyncCallback<List<int[]>> asyncCallback);
}
