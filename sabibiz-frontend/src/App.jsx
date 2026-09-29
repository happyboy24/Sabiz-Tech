import React, { useState, useEffect } from 'react';
import { useAuth } from './context/AuthContext';
import api from './api/client';
import { Sidebar } from './components/Sidebar';
import { Header } from './components/Header';
import { LoginView } from './components/LoginView';
import { DashboardView } from './components/DashboardView';
import { PosView } from './components/PosView';
import { ProductsView } from './components/ProductsView';
import { CategoriesView } from './components/CategoriesView';
import { CustomersView } from './components/CustomersView';
import { SuppliersView } from './components/SuppliersView';
import { ExpensesView } from './components/ExpensesView';
import { DebtsView } from './components/DebtsView';
import { SalesView } from './components/SalesView';
import { BusinessView } from './components/BusinessView';

import {
  INITIAL_PRODUCTS,
  INITIAL_CATEGORIES,
  INITIAL_CUSTOMERS,
  INITIAL_SUPPLIERS,
  INITIAL_SALES,
  INITIAL_EXPENSES,
  INITIAL_DEBTS,
} from './api/mockData';

export default function App() {
  const { user, logout } = useAuth();
  const [activeTab, setActiveTab] = useState('dashboard');

  // Application Data States (synced with API when live, initial fallback data otherwise)
  const [products, setProducts] = useState(INITIAL_PRODUCTS);
  const [categories, setCategories] = useState(INITIAL_CATEGORIES);
  const [customers, setCustomers] = useState(INITIAL_CUSTOMERS);
  const [suppliers, setSuppliers] = useState(INITIAL_SUPPLIERS);
  const [sales, setSales] = useState(INITIAL_SALES);
  const [expenses, setExpenses] = useState(INITIAL_EXPENSES);
  const [debts, setDebts] = useState(INITIAL_DEBTS);

  // Fetch real data from Spring Boot API on mount if authenticated
  useEffect(() => {
    if (!user) return;

    const fetchData = async () => {
      try {
        const [prodRes, catRes, custRes, suppRes, salesRes, expRes, debtRes] = await Promise.allSettled([
          api.get('/products'),
          api.get('/categories'),
          api.get('/customers'),
          api.get('/suppliers'),
          api.get('/sales'),
          api.get('/expenses'),
          api.get('/debts'),
        ]);

        if (prodRes.status === 'fulfilled' && prodRes.value.data?.content?.length) {
          setProducts(prodRes.value.data.content);
        }
        if (catRes.status === 'fulfilled' && catRes.value.data?.content?.length) {
          setCategories(catRes.value.data.content);
        }
        if (custRes.status === 'fulfilled' && custRes.value.data?.content?.length) {
          setCustomers(custRes.value.data.content);
        }
        if (suppRes.status === 'fulfilled' && suppRes.value.data?.content?.length) {
          setSuppliers(suppRes.value.data.content);
        }
        if (salesRes.status === 'fulfilled' && salesRes.value.data?.content?.length) {
          setSales(salesRes.value.data.content);
        }
        if (expRes.status === 'fulfilled' && expRes.value.data?.content?.length) {
          setExpenses(expRes.value.data.content);
        }
        if (debtRes.status === 'fulfilled' && debtRes.value.data?.content?.length) {
          setDebts(debtRes.value.data.content);
        }
      } catch (err) {
        console.log('Using local fallback state for smooth interactive session.');
      }
    };

    fetchData();
  }, [user]);

  if (!user) {
    return <LoginView />;
  }

  // Action Handlers
  const handleCheckout = async (saleRecord) => {
    setSales((prev) => [saleRecord, ...prev]);

    // Update Product Stock Levels locally
    setProducts((prev) =>
      prev.map((prod) => {
        const soldItem = saleRecord.items.find((i) => i.id === prod.id);
        if (soldItem) {
          return { ...prod, quantity: Math.max(0, prod.quantity - soldItem.qty) };
        }
        return prod;
      })
    );

    // Attempt sync with backend API
    try {
      await api.post('/sales', saleRecord);
    } catch (e) {
      console.log('Recorded sale locally.');
    }
  };

  const handleAddProduct = async (newProd) => {
    setProducts((prev) => [newProd, ...prev]);
    try {
      await api.post('/products', newProd);
    } catch (e) {
      console.log('Saved product locally.');
    }
  };

  const handleDeleteProduct = async (id) => {
    setProducts((prev) => prev.filter((p) => p.id !== id));
    try {
      await api.delete(`/products/${id}`);
    } catch (e) {
      console.log('Deleted product locally.');
    }
  };

  const handleAddCategory = async (newCat) => {
    setCategories((prev) => [...prev, newCat]);
    try {
      await api.post('/categories', newCat);
    } catch (e) {
      console.log('Saved category locally.');
    }
  };

  const handleAddCustomer = async (newCust) => {
    setCustomers((prev) => [newCust, ...prev]);
    try {
      await api.post('/customers', newCust);
    } catch (e) {
      console.log('Saved customer locally.');
    }
  };

  const handleAddSupplier = async (newSupp) => {
    setSuppliers((prev) => [newSupp, ...prev]);
    try {
      await api.post('/suppliers', newSupp);
    } catch (e) {
      console.log('Saved supplier locally.');
    }
  };

  const handleAddExpense = async (newExp) => {
    setExpenses((prev) => [newExp, ...prev]);
    try {
      await api.post('/expenses', newExp);
    } catch (e) {
      console.log('Saved expense locally.');
    }
  };

  const handleAddDebt = async (newDebt) => {
    setDebts((prev) => [newDebt, ...prev]);
    try {
      await api.post('/debts', newDebt);
    } catch (e) {
      console.log('Saved debt locally.');
    }
  };

  const handleSettleDebt = (id, amountPaid) => {
    setDebts((prev) =>
      prev.map((d) =>
        d.id === id ? { ...d, paidAmount: (d.paidAmount || 0) + amountPaid, status: 'SETTLED' } : d
      )
    );
  };

  return (
    <div className="flex h-screen bg-slate-950 text-slate-100 overflow-hidden font-sans">
      <Sidebar activeTab={activeTab} setActiveTab={setActiveTab} user={user} onLogout={logout} />

      <div className="flex-1 flex flex-col min-w-0 overflow-hidden">
        <Header activeTab={activeTab} user={user} />

        <main className="flex-1 overflow-y-auto p-6">
          {activeTab === 'dashboard' && (
            <DashboardView
              products={products}
              sales={sales}
              expenses={expenses}
              debts={debts}
              setActiveTab={setActiveTab}
            />
          )}

          {activeTab === 'pos' && (
            <PosView products={products} customers={customers} onCheckout={handleCheckout} />
          )}

          {activeTab === 'products' && (
            <ProductsView
              products={products}
              categories={categories}
              onAddProduct={handleAddProduct}
              onDeleteProduct={handleDeleteProduct}
            />
          )}

          {activeTab === 'categories' && (
            <CategoriesView categories={categories} onAddCategory={handleAddCategory} />
          )}

          {activeTab === 'sales' && <SalesView sales={sales} />}

          {activeTab === 'customers' && (
            <CustomersView customers={customers} onAddCustomer={handleAddCustomer} />
          )}

          {activeTab === 'suppliers' && (
            <SuppliersView suppliers={suppliers} onAddSupplier={handleAddSupplier} />
          )}

          {activeTab === 'expenses' && (
            <ExpensesView expenses={expenses} onAddExpense={handleAddExpense} />
          )}

          {activeTab === 'debts' && (
            <DebtsView debts={debts} onAddDebt={handleAddDebt} onSettleDebt={handleSettleDebt} />
          )}

          {activeTab === 'business' && <BusinessView user={user} />}
        </main>
      </div>
    </div>
  );
}
