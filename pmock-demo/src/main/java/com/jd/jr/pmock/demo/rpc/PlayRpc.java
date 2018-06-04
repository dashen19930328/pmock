package com.jd.jr.pmock.demo.rpc;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 游玩
 * User: yangkuan@jd.com
 * Date: 18-6-1
 * Time: 下午7:33
 */
@Path("/playRpc")
public interface PlayRpc {
    @POST
    @Path("/play")
    public String play();
}
