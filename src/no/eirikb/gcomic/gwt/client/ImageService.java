/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.gcomic.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 *
 * @author eirikb
 */
public interface ImageService extends RemoteService {

    public String test(String text);

    public List<int[]> getImage(String imageURL);
}
