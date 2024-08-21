import apiUrl from "../config/api";
import axios from "axios";

export interface ProductData{
    productName: string;
    productDescription?: string;
    productSKU: string;
    productCategory: string;
    productQuantity: number;
    productPrice: number;
}

interface InventoryItem {
    productId: number;
    name: string;
    sku: string;
    quantity: number;
    price: number;
    category: string;
    description: string;
}

export async function fetchSkuNumber(category: string) {
    try
    {
        const response  = await axios.get(`${apiUrl}/api/skus/`, {
            params:{
                category: category
            }
        });
        console.log('Sku Response: ', response.data);
        if(response.status === 200){
            return response.data;
        }
    }catch(error){
        console.error('There was an error generating a sku number: ', error);
    }
}

export async function deleteProductFromInventory(productId: number) : Promise<void> {
    if(productId === null)
    {
        console.error('Attempted to delete item with undefined ID');
        return;
    }

    try
    {
        const response = await axios.delete(`${apiUrl}/api/products/${productId}`, {
            method: 'DELETE'
        });

    }catch(err)
    {
        console.error('There was an error deleting the product from our inventory: ', err);
        throw err;
    }
}


export async function fetchAllProductsInInventory() {
    try
    {
        const response = await axios.get<InventoryItem[]>(`${apiUrl}/api/products/`);

        console.log('Inventory Items: ', response.data);
        return response.data ?? [];

    }catch(err)
    {
        console.error('There was an error fetching all the products in the inventory: ', err);
    }
}

export async function addProductToInventory(product: ProductData) : Promise<any>
{
    const {productName, productDescription, productSKU, productCategory, productQuantity, productPrice} = product;
    console.log('Product to add: ', product);
    try
    {
        const response = await axios.post<ProductData>(`${apiUrl}/api/products/`, {
            productName,
            productDescription,
            productSKU,
            productCategory,
            productPrice,
            productQuantity,
        });

        console.log('Response Status: ', response.status);
        return response.data;
    }catch(err)
    {
        console.error("There was an error adding the product to our inventory: ", err);
    }
}