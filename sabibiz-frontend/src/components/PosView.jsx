import React, { useState } from 'react';
import { 
  Search, 
  Plus, 
  Trash2, 
  ShoppingCart, 
  CheckCircle, 
  User, 
  CreditCard,
  DollarSign,
  Printer
} from 'lucide-react';

export const PosView = ({ products, customers, onCheckout }) => {
  const [cart, setCart] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('CASH');
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('ALL');

  const categories = ['ALL', ...new Set(products.map((p) => p.categoryName || 'General'))];

  const filteredProducts = products.filter((p) => {
    const matchesSearch = p.name.toLowerCase().includes(searchQuery.toLowerCase()) || 
                          p.sku?.toLowerCase().includes(searchQuery.toLowerCase());
    const matchesCategory = selectedCategory === 'ALL' || p.categoryName === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  const addToCart = (product) => {
    if (product.quantity <= 0) {
      alert('Product out of stock!');
      return;
    }

    setCart((prev) => {
      const existing = prev.find((item) => item.id === product.id);
      if (existing) {
        if (existing.qty >= product.quantity) {
          alert('Cannot add more than available stock limit.');
          return prev;
        }
        return prev.map((item) =>
          item.id === product.id ? { ...item, qty: item.qty + 1 } : item
        );
      }
      return [...prev, { ...product, qty: 1 }];
    });
  };

  const updateQuantity = (id, delta) => {
    setCart((prev) =>
      prev
        .map((item) => {
          if (item.id === id) {
            const newQty = item.qty + delta;
            return newQty > 0 ? { ...item, qty: newQty } : null;
          }
          return item;
        })
        .filter(Boolean)
    );
  };

  const removeFromCart = (id) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  const subtotal = cart.reduce((sum, item) => sum + item.price * item.qty, 0);
  const tax = subtotal * 0.075; // 7.5% VAT
  const total = subtotal + tax;

  const handleCompleteSale = () => {
    if (cart.length === 0) {
      alert('Cart is empty!');
      return;
    }

    const saleRecord = {
      id: Date.now(),
      saleNumber: `SAL-${Date.now().toString().slice(-6)}`,
      customerName: customers.find((c) => c.id === Number(selectedCustomer))?.name || 'Walk-in Customer',
      items: cart,
      totalAmount: total,
      paidAmount: total,
      paymentMethod,
      status: 'COMPLETED',
      createdAt: new Date().toISOString(),
    };

    onCheckout(saleRecord);
    setCart([]);
    alert(`Sale completed successfully! Receipt generated for ₦${total.toLocaleString()}`);
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 h-[calc(100vh-6rem)]">
      {/* Product Catalog Section (7 Cols) */}
      <div className="lg:col-span-7 flex flex-col h-full bg-slate-900 border border-slate-800 rounded-2xl p-5 overflow-hidden">
        {/* Search & Filter Header */}
        <div className="space-y-3 mb-4">
          <div className="relative">
            <Search className="w-4 h-4 text-slate-400 absolute left-3.5 top-3" />
            <input
              type="text"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder="Scan or search by product name, SKU..."
              className="w-full pl-10 pr-4 py-2.5 bg-slate-950 border border-slate-800 rounded-xl text-sm text-white placeholder-slate-500 focus:outline-none focus:border-indigo-500"
            />
          </div>

          {/* Category Tabs */}
          <div className="flex space-x-2 overflow-x-auto pb-1 scrollbar-none">
            {categories.map((cat) => (
              <button
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap transition-colors ${
                  selectedCategory === cat
                    ? 'bg-indigo-600 text-white'
                    : 'bg-slate-950 text-slate-400 hover:text-slate-200 border border-slate-800'
                }`}
              >
                {cat}
              </button>
            ))}
          </div>
        </div>

        {/* Product Cards Grid */}
        <div className="flex-1 overflow-y-auto pr-1 grid grid-cols-2 sm:grid-cols-3 gap-3">
          {filteredProducts.map((product) => {
            const outOfStock = product.quantity <= 0;
            return (
              <div
                key={product.id}
                onClick={() => !outOfStock && addToCart(product)}
                className={`p-3.5 rounded-xl bg-slate-950 border transition-all cursor-pointer flex flex-col justify-between ${
                  outOfStock
                    ? 'border-slate-800 opacity-50 cursor-not-allowed'
                    : 'border-slate-800/80 hover:border-indigo-500/50 hover:bg-slate-800/30'
                }`}
              >
                <div>
                  <span className="text-[10px] uppercase font-bold text-indigo-400 block tracking-wider">
                    {product.categoryName || 'General'}
                  </span>
                  <h4 className="text-xs font-semibold text-white mt-1 line-clamp-2">{product.name}</h4>
                  <span className="text-[11px] text-slate-500 font-mono block mt-0.5">{product.sku}</span>
                </div>

                <div className="mt-3 flex items-center justify-between">
                  <span className="text-sm font-extrabold text-emerald-400">
                    ₦{product.price?.toLocaleString()}
                  </span>
                  <span
                    className={`text-[10px] font-bold px-1.5 py-0.5 rounded ${
                      outOfStock ? 'bg-rose-500/10 text-rose-400' : 'bg-slate-800 text-slate-300'
                    }`}
                  >
                    {outOfStock ? 'Out of Stock' : `${product.quantity} ${product.unit || 'pcs'}`}
                  </span>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      {/* Cart & Checkout Section (5 Cols) */}
      <div className="lg:col-span-5 flex flex-col h-full bg-slate-900 border border-slate-800 rounded-2xl p-5">
        <div className="flex items-center justify-between pb-3 border-b border-slate-800">
          <div className="flex items-center space-x-2">
            <ShoppingCart className="w-5 h-5 text-indigo-400" />
            <h3 className="font-bold text-white text-base">Current Cart</h3>
          </div>
          <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-indigo-500/10 text-indigo-400 border border-indigo-500/20">
            {cart.reduce((sum, item) => sum + item.qty, 0)} Items
          </span>
        </div>

        {/* Customer Selector */}
        <div className="mt-3">
          <label className="block text-[11px] font-semibold text-slate-400 uppercase tracking-wider mb-1">
            Customer Account
          </label>
          <div className="relative">
            <User className="w-4 h-4 text-slate-500 absolute left-3 top-2.5" />
            <select
              value={selectedCustomer}
              onChange={(e) => setSelectedCustomer(e.target.value)}
              className="w-full pl-9 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-xs text-white focus:outline-none focus:border-indigo-500"
            >
              <option value="">Walk-in Customer (General)</option>
              {customers.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name} ({c.phone})
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Cart Itemized List */}
        <div className="flex-1 overflow-y-auto py-3 space-y-2 my-2 border-y border-slate-800/80">
          {cart.length === 0 ? (
            <div className="h-full flex flex-col items-center justify-center text-slate-500 py-10">
              <ShoppingCart className="w-10 h-10 mb-2 opacity-40" />
              <p className="text-xs">No items in cart yet</p>
              <p className="text-[11px] text-slate-600">Click products on the left to add</p>
            </div>
          ) : (
            cart.map((item) => (
              <div key={item.id} className="p-2.5 rounded-xl bg-slate-950 border border-slate-800 flex items-center justify-between text-xs">
                <div className="flex-1 min-w-0 pr-2">
                  <p className="font-semibold text-slate-200 truncate">{item.name}</p>
                  <p className="text-[11px] text-emerald-400 font-bold">₦{item.price?.toLocaleString()} each</p>
                </div>

                {/* Qty Controls */}
                <div className="flex items-center space-x-2">
                  <div className="flex items-center border border-slate-800 bg-slate-900 rounded-lg">
                    <button
                      onClick={() => updateQuantity(item.id, -1)}
                      className="px-2 py-0.5 text-slate-300 hover:text-white"
                    >
                      -
                    </button>
                    <span className="px-2 py-0.5 font-bold text-white text-xs">{item.qty}</span>
                    <button
                      onClick={() => updateQuantity(item.id, 1)}
                      className="px-2 py-0.5 text-slate-300 hover:text-white"
                    >
                      +
                    </button>
                  </div>
                  <button
                    onClick={() => removeFromCart(item.id)}
                    className="p-1 text-slate-500 hover:text-rose-400 transition-colors"
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>
                </div>
              </div>
            ))
          )}
        </div>

        {/* Payment Summary */}
        <div className="space-y-2 text-xs text-slate-400 pt-2">
          <div className="flex justify-between">
            <span>Subtotal</span>
            <span className="font-mono text-slate-200">₦{subtotal.toLocaleString()}</span>
          </div>
          <div className="flex justify-between">
            <span>VAT (7.5%)</span>
            <span className="font-mono text-slate-200">₦{tax.toLocaleString()}</span>
          </div>
          <div className="flex justify-between text-sm font-bold text-white pt-2 border-t border-slate-800">
            <span>Total Payable</span>
            <span className="font-mono text-emerald-400">₦{total.toLocaleString()}</span>
          </div>

          {/* Payment Method Tabs */}
          <div className="pt-2">
            <label className="block text-[11px] font-semibold text-slate-400 uppercase tracking-wider mb-1">
              Payment Method
            </label>
            <div className="grid grid-cols-3 gap-2">
              {['CASH', 'TRANSFER', 'POS_CARD'].map((method) => (
                <button
                  key={method}
                  onClick={() => setPaymentMethod(method)}
                  className={`py-1.5 rounded-lg text-[11px] font-bold transition-all border ${
                    paymentMethod === method
                      ? 'bg-indigo-600 text-white border-indigo-500'
                      : 'bg-slate-950 text-slate-400 border-slate-800 hover:border-slate-700'
                  }`}
                >
                  {method.replace('_', ' ')}
                </button>
              ))}
            </div>
          </div>

          {/* Complete Checkout Button */}
          <button
            onClick={handleCompleteSale}
            className="w-full mt-3 py-3 bg-emerald-600 hover:bg-emerald-500 active:bg-emerald-700 text-white font-bold text-sm rounded-xl shadow-lg shadow-emerald-600/30 transition-all flex items-center justify-center space-x-2"
          >
            <CheckCircle className="w-4 h-4" />
            <span>Complete Order (₦{total.toLocaleString()})</span>
          </button>
        </div>
      </div>
    </div>
  );
};
