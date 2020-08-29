package com.gibreelm.gapsi.model;

import java.util.List;

public class Record{
    public String productId;
    public String skuRepositoryId;
    public String productDisplayName;
    public String productType;
    public int productRatingCount;
    public double productAvgRating;
    public double listPrice;
    public double minimumListPrice;
    public double maximumListPrice;
    public double promoPrice;
    public double minimumPromoPrice;
    public double maximumPromoPrice;
    public boolean isHybrid;
    public String marketplaceSLMessage;
    public String marketplaceBTMessage;
    public boolean isMarketPlace;
    public boolean isImportationProduct;
    public String brand;
    public String seller;
    public String category;
    public String smImage;
    public String lgImage;
    public String xlImage;
    public String groupType;
    public List<Object> plpFlags;
    public List<VariantsColor> variantsColor;
}