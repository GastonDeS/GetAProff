package ar.edu.itba.paw.webapp.dto;

public class MaxPriceDto {
    private int price;

    public static MaxPriceDto fromPrice(int price){
        MaxPriceDto maxPriceDto = new MaxPriceDto();
        maxPriceDto.price = price;
        return maxPriceDto;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
