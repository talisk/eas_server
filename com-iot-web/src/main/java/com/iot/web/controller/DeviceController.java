package com.iot.web.controller;

import com.google.gson.reflect.TypeToken;
import com.iot.domain.Device;
import com.iot.domain.dic.ResultCode;
import com.iot.domain.vo.Result;
import com.iot.service.DeviceService;
import com.iot.utils.CommonUtils;
import com.iot.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/device")
public class DeviceController extends ControllerBase {

    private static final Type DeviceType = new TypeToken<Result<ArrayList<Device>>>() {
    }.getType();

    @Autowired
    private DeviceService deviceService;

    /**
     * 插入设备
     *
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = CommonUtils.MediaTypeJSON)
    @ResponseBody
    public String add(HttpServletRequest request) {
        Result<Long> result = new Result<Long>();
        try {
            String json = getRequestEntity(request);
            Device device = JsonUtils.fromJson(json, Device.class);
            if (device != null) {
                long deviceId = deviceService.add(device);
                if (deviceId > 0) {
                    result.setCode(ResultCode.USER_OK);
                    result.setData(deviceId);
                } else {
                    result.setCode(ResultCode.USER_FAILURE);
                    result.setMsg("创建失败！");
                }
            } else {
                result.setCode(ResultCode.ERROR);
                result.setMsg("参数错误！");
            }
        } catch (Exception ex) {
            result.setMsg("创建失败！");
            logger.error(ex);
        }

        result.setTimestamp(new Date());
        return JsonUtils.toJson(result, ResponseResultType.LongType);
    }

    /**
     * 得到设备列表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = CommonUtils.MediaTypeJSON)
    @ResponseBody
    public String getList(@RequestParam long userId, @RequestParam int pageIndex, @RequestParam int pageSize) {

        Result<List<Device>> result = new Result<List<Device>>();

        try {
            List<Device> deviceList = deviceService.getList(userId, pageIndex, pageSize);
            result.setCode(ResultCode.USER_OK);
            result.setData(deviceList);
        } catch (Exception ex) {
            result.setCode(ResultCode.ERROR);
            logger.error(ex);
        }

        result.setTimestamp(new Date());
        return JsonUtils.toJson(result, DeviceType);
    }

    /**
     * 得到设备总数
     *
     * @return
     */
    @RequestMapping(value = "count", method = RequestMethod.GET, produces = CommonUtils.MediaTypeJSON)
    @ResponseBody
    public String getCount(@RequestParam long userId) {
        Result<Long> result = new Result<Long>();

        try {
            Long count = deviceService.getCount(userId);
            result.setCode(ResultCode.USER_OK);
            result.setData(count);
        } catch (Exception ex) {
            result.setCode(ResultCode.ERROR);
            logger.error(ex);
        }

        result.setTimestamp(new Date());
        Type longType = new TypeToken<Result<Long>>() {
        }.getType();
        return JsonUtils.toJson(result, longType);
    }
}
