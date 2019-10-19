package com.formu.Control;

import com.formu.Service.IMesService;
import com.formu.Utils.Msg;
import com.formu.bean.Comment;
import com.formu.bean.Mes;
import com.formu.common.Common;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by weiqiang
 */
@RestController
@RequestMapping("/mes/")
public class MesControl {

    @Autowired
    private IMesService mesService;

    @Autowired
    private Common common;


    @ApiOperation(value = "获取所有私信", notes = "")
    @RequestMapping(value = "getall.do",method = RequestMethod.GET)
    public Msg getall(HttpServletRequest request){
        return mesService.getMesUsers(common.getid(request));
    }



    @ApiOperation(value = "获取某个私信", notes = "")
    @ApiImplicitParam(name = "mesId", value = "私信的id", paramType = "path", dataType = "Integer")
    @RequestMapping(value = "getall.do/{mesId}",method = RequestMethod.GET)
    public Msg get(HttpServletRequest request,@PathVariable("mesId")int mesId){
        return mesService.getById(mesId);
    }

    @ApiOperation(value = "删除私信", notes = "")
    @ApiImplicitParam(name = "mesId", value = "私信的id", paramType = "path", dataType = "Integer")
    @RequestMapping(value = "delete.do/{mesId}",method = RequestMethod.DELETE)
    public Msg delete(HttpServletRequest request, @PathVariable("mesId")int mesId){
        return mesService.deleteByid(mesId,common.getid(request));
    }


    @ApiOperation(value = "发送私信", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "收私信人的id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "message", value = "私信内容", required = true, dataType = "String")
    })
    @RequestMapping(value = "send.do",method = RequestMethod.POST)
    public Msg send(HttpServletRequest request,
                    @RequestParam("userid") int userid,
                    @RequestParam("message")String message){
        Mes mes = new Mes();
        mes.setMesIsread(0);
        mes.setMesFromid(common.getid(request));
        mes.setMesMessage(message);
        return mesService.insert(mes);
    }

}
