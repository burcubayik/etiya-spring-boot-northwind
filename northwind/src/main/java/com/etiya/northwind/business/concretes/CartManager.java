package com.etiya.northwind.business.concretes;

import com.etiya.northwind.business.abstracts.CartService;
import com.etiya.northwind.business.abstracts.OrderDetailService;
import com.etiya.northwind.business.abstracts.OrderService;
import com.etiya.northwind.business.requests.carts.CreateCartRequest;
import com.etiya.northwind.business.requests.carts.DeleteCartRequest;
import com.etiya.northwind.business.requests.carts.SaleCartRequest;
import com.etiya.northwind.business.requests.carts.UpdateCartRequest;
import com.etiya.northwind.business.responses.carts.CartListResponse;
import com.etiya.northwind.business.responses.carts.ReadCartResponse;
import com.etiya.northwind.business.responses.orderDetails.OrderDetailListResponse;
import com.etiya.northwind.business.responses.orderDetails.ReadOrderDetailResponse;
import com.etiya.northwind.core.utilities.exceptions.BusinessException;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.core.utilities.results.DataResult;
import com.etiya.northwind.core.utilities.results.Result;
import com.etiya.northwind.core.utilities.results.SuccessDataResult;
import com.etiya.northwind.core.utilities.results.SuccessResult;
import com.etiya.northwind.dataAccess.CartRepository;
import com.etiya.northwind.entities.concretes.Cart;
import com.etiya.northwind.entities.concretes.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CartManager implements CartService {

    private ModelMapperService modelMapperService;
    private CartRepository cartRepository;
    private OrderDetailService orderDetailService;
    private OrderService orderService;

    public CartManager(ModelMapperService modelMapperService, CartRepository cartRepository,
                       OrderDetailService orderDetailService,OrderService orderService) {
        this.modelMapperService = modelMapperService;
        this.cartRepository = cartRepository;
       this.orderDetailService = orderDetailService;
       this.orderService=orderService;
    }

    @Override
    public DataResult<List<CartListResponse>> getAll() {

        List<Cart> results = cartRepository.findAll();
        List<CartListResponse> response = results.stream().map(cart -> this.modelMapperService.forResponse()
                .map(cart,CartListResponse.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response);

    }

    @Override
    public Result add(CreateCartRequest createCartRequest) {

        Cart cart = this.modelMapperService.forRequest().map(createCartRequest, Cart.class);
       // Cart cart=new Cart();
       // cart.setCustomerId(createCartRequest.getCustomerId());
       // cart.setProductId(createCartRequest.getProductId());

        //this.cartRepository.findById(createCartRequest.getCartId()).get();
       // CreateCartRequest createCartRequest1 = new CreateCartRequest();
       // createCartRequest1.setCartId(cart1.getCartId());

        this.cartRepository.save(cart);

        return new SuccessResult("CART.ADDED");

    }

    @Override
    public Result update(UpdateCartRequest updateCartRequest) {
        Cart cart = this.modelMapperService.forRequest().map(updateCartRequest, Cart.class);
        this.cartRepository.save(cart);
        return new SuccessResult("CART.UPDATED");
    }

    @Override
    public Result delete(DeleteCartRequest deleteCartRequest) {
        this.cartRepository.deleteCartWithCustomerAndProductId(deleteCartRequest.getCustomerId(), deleteCartRequest.getProductId());
        return new SuccessResult("CART.DELETED");
    }

    @Override
    public DataResult<ReadCartResponse> getById(String customerId, int productId) {
        Cart cart = this.cartRepository.getByCustomer_CustomerIdAndProduct_ProductId(customerId,productId);
        ReadCartResponse response = this.modelMapperService.forResponse().map(cart, ReadCartResponse.class);
        return new SuccessDataResult<>(response);
    }

    @Override
    public Result sales(SaleCartRequest salesCartRequest) {
        checkIfSales();
        Cart cart = this.cartRepository.getByCustomer_CustomerIdAndProduct_ProductId(salesCartRequest.getCustomerId(), salesCartRequest.getProductId());
        salesCartRequest.getCreateOrderRequest().setCustomerId(cart.getCustomerId());
        salesCartRequest.getCreateOrderDetailRequest().setProductId(cart.getProductId());

        this.orderService.add(salesCartRequest.getCreateOrderRequest());
        this.orderDetailService.add(salesCartRequest.getCreateOrderDetailRequest());

        this.cartRepository.deleteCartWithCustomerAndProductId(salesCartRequest.getCustomerId(), salesCartRequest.getProductId());
        return new SuccessResult("Sales.Success");
    }


    private void checkIfSales(){
        boolean isSales= true;
        if(!isSales){
            throw new BusinessException("satış yapılmadı");
        }
    }
}
