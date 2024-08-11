import apiUrl from "../config/api";

export interface ProductData{
    productName: string;
    productDescription?: string;
    productSKU: string;
    productCategory: string;
    productQuantity: number;
    productPrice: number;
}


export async function deleteProductFromInventory(productId: number) : Promise<void> {
    try
    {
        const response = await fetch(`${apiUrl}/api/products/${productId}`, {
            method: 'DELETE'
        });
        if(!response.ok)
        {
            throw new Error(`HTTP Error status: ${response.status}`);
        }
    }catch(err)
    {
        console.error('There was an error deleting the product from our inventory: ', err);
        throw err;
    }
}


export async function fetchAllProductsInInventory() {
    try
    {
        const response = await fetch(`${apiUrl}/api/products/`);

        console.log('Inventory Items: ', response.body);
        if(!response.ok)
        {
            throw new Error(`HTTP Error status: ${response.status}`);
        }

        return await response.json();

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
        const response = await fetch(`${apiUrl}/api/products/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify({
                productName: productName,
                productDescription: productDescription,
                productSKU: productSKU,
                productCategory: productCategory,
                productPrice: productPrice,
                productQuantity: productQuantity,
            }),
        });
        console.log('Response Status: ', response.status);
        if(!response.ok)
        {
            throw new Error(`HTTP Error status: ${response.status}`);
        }
        return await response.json();
    }catch(err)
    {
        console.error("There was an error adding the product to our inventory: ", err);
    }
}