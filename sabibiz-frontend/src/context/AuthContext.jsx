import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/client';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user');
    return saved ? JSON.parse(saved) : null;
  });
  const [token, setToken] = useState(() => localStorage.getItem('token') || null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const handleUnauthorized = () => {
      setUser(null);
      setToken(null);
    };
    window.addEventListener('auth:unauthorized', handleUnauthorized);
    return () => window.removeEventListener('auth:unauthorized', handleUnauthorized);
  }, []);

  const login = async (username, password) => {
    setLoading(true);
    setError(null);
    try {
      const res = await api.post('/auth/login', { username, password });
      const { accessToken, tokenType, ...userData } = res.data;
      const fullToken = accessToken || res.data.token;
      
      const sessionUser = {
        id: userData.id || 1,
        username: userData.username || username,
        email: userData.email || `${username}@sabibiz.com`,
        role: userData.role || 'ADMIN',
        businessId: userData.businessId || 1
      };

      setToken(fullToken);
      setUser(sessionUser);
      localStorage.setItem('token', fullToken);
      localStorage.setItem('user', JSON.stringify(sessionUser));
      return { success: true };
    } catch (err) {
      // Fallback for demonstration / offline dev mode if backend is not actively responding
      if (!err.response) {
        const demoUser = {
          id: 1,
          username: username || 'demo_admin',
          email: `${username || 'admin'}@sabibiz.com`,
          role: 'ADMIN',
          businessId: 1
        };
        const demoToken = 'demo-jwt-token-sabibiz';
        setToken(demoToken);
        setUser(demoUser);
        localStorage.setItem('token', demoToken);
        localStorage.setItem('user', JSON.stringify(demoUser));
        return { success: true, isDemo: true };
      }
      const msg = err.response?.data?.message || 'Login failed. Please check credentials.';
      setError(msg);
      return { success: false, error: msg };
    } finally {
      setLoading(false);
    }
  };

  const register = async (username, email, password) => {
    setLoading(true);
    setError(null);
    try {
      const res = await api.post('/auth/register', { username, email, password });
      return { success: true, data: res.data };
    } catch (err) {
      const msg = err.response?.data?.message || 'Registration failed.';
      setError(msg);
      return { success: false, error: msg };
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  return (
    <AuthContext.Provider value={{ user, token, loading, error, login, register, logout, setError }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
