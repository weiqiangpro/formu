package com.formu.Control;

import com.formu.Service.IMesService;
import com.formu.Utils.Msg;
import com.formu.bean.vo.Mes;
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


    @ApiOperation(value = "获取接受到私信列表" )
    @RequestMapping(value = "getreceive.do",method = RequestMethod.GET)
    public Msg getMessages(HttpServletRequest request){
        return mesService.getMesUsers(common.getid(request));
    }

    @ApiOperation(value = "获取已发送的到私信列表" )
    @RequestMapping(value = "getsend.do",method = RequestMethod.GET)
    public Msg getSendMessages(HttpServletRequest request){
        return mesService.getSendMesUsers(common.getid(request));
    }

    @ApiOperation(value = "获取某个私信" )
    @ApiImplicitParam(name = "mesId", value = "私信的id", paramType = "path", dataType = "int")
    @RequestMapping(value = "getall.do/{mesId}",method = RequestMethod.GET)
    public Msg getByMessageId(HttpServletRequest request,@PathVariable("mesId")int mesId){
        return mesService.getById(mesId,common.getid(request));
    }

    @ApiOperation(value = "删除私信" )
    @ApiImplicitParam(name = "mesId", value = "私信的id", paramType = "path", dataType = "int")
    @RequestMapping(value = "delete.do/{mesId}",method = RequestMethod.DELETE)
    public Msg delete(HttpServletRequest request, @PathVariable("mesId")int mesId){
        return mesService.deleteByid(mesId,common.getid(request));
    }


    @ApiOperation(value = "发送私信" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "收私信人的id", required = true, dataType = "int"),
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
