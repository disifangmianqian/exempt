package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;

import njurestaurant.njutakeout.data.dao.order.PlatformOrderDao;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.OrderWrongInputException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.parameters.order.TestParameters;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OneOrderResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final PlatformOrderBlService platformOrderBlService;
    private final PlatformOrderDataService platformOrderDataService;

    @Autowired
    public OrderController(PlatformOrderBlService platformOrderBlService, PlatformOrderDao platformOrderDao, PlatformOrderDataService platformOrderDataService) {
        this.platformOrderBlService = platformOrderBlService;
        this.platformOrderDataService = platformOrderDataService;
    }

    @ApiOperation(value = "订单明细", notes = "查看全部订单明细")
    @RequestMapping(value = "order/list", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderListResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getOrders(@PageableDefault(value = 3000 ,sort = { "time" }, direction = Sort.Direction.DESC )@RequestParam("condition") String condition, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new ResponseEntity<>(new JSONResponse(200,  platformOrderBlService.findAllPlatformOrders(condition ,page,size)), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "修改订单",actionType = "3")
    @ApiOperation(value = "修改订单", notes = "修改订单")
    @RequestMapping(value = "order/list/update/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getOrder(@PathVariable("id")int id, @RequestBody PlatformUpdateParameters platformUpdateParameters) {
        try {
            platformOrderBlService.updatePlatformOrder(id, platformUpdateParameters);
            return new ResponseEntity<>(new JSONResponse(200, "补单成功" ), HttpStatus.OK);
        } catch (OrderWrongInputException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(e.getResponse().getInfoCode(),e.getResponse().getDescription()),HttpStatus.OK);
        } catch (BlankInputException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new JSONResponse(e.getResponse().getInfoCode(),e.getResponse().getDescription()),HttpStatus.OK);
        }
    }
    @ApiOperation(value = "查看某个订单", notes = "查看某个订单")
    @RequestMapping(value = "order/list/{number}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderListResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getOrderByNumber(@PathVariable("number") String number) {//根据订单号查订单
        PlatformOrder platformOrder = platformOrderBlService.findPlatformOrderByNumber(number);
        return new ResponseEntity<>(new JSONResponse(200, new OneOrderResponse(String.valueOf(platformOrder.getTime().getTime()),platformOrder.getState())), HttpStatus.OK);
    }

}
