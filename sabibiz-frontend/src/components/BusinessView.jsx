import React from 'react';
import { Building, Mail, Phone, MapPin, Globe, ShieldCheck, Key } from 'lucide-react';

export const BusinessView = ({ user }) => {
  return (
    <div className="max-w-4xl space-y-6">
      <div className="bg-slate-900 border border-slate-800 rounded-2xl p-6 space-y-6">
        <div className="flex items-center space-x-4 border-b border-slate-800 pb-5">
          <div className="w-16 h-16 rounded-2xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center text-indigo-400 font-extrabold text-2xl shadow-lg">
            SB
          </div>
          <div>
            <h3 className="text-xl font-bold text-white">Sabiz Technologies Ltd</h3>
            <p className="text-xs text-slate-400">Enterprise Retail & Inventory Operations</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 text-xs">
          <div className="space-y-4">
            <h4 className="font-semibold text-slate-200 uppercase tracking-wider text-[11px]">Contact & Details</h4>
            
            <div className="space-y-2 text-slate-300">
              <div className="flex items-center space-x-3 p-3 rounded-xl bg-slate-950 border border-slate-800">
                <Building className="w-4 h-4 text-indigo-400" />
                <span>Business Reg #: RC-99882103</span>
              </div>
              <div className="flex items-center space-x-3 p-3 rounded-xl bg-slate-950 border border-slate-800">
                <Mail className="w-4 h-4 text-indigo-400" />
                <span>support@sabiztech.com</span>
              </div>
              <div className="flex items-center space-x-3 p-3 rounded-xl bg-slate-950 border border-slate-800">
                <Phone className="w-4 h-4 text-indigo-400" />
                <span>+234 800 SABIZ TECH</span>
              </div>
              <div className="flex items-center space-x-3 p-3 rounded-xl bg-slate-950 border border-slate-800">
                <MapPin className="w-4 h-4 text-indigo-400" />
                <span>Victoria Island HQ, Lagos, Nigeria</span>
              </div>
            </div>
          </div>

          <div className="space-y-4">
            <h4 className="font-semibold text-slate-200 uppercase tracking-wider text-[11px]">System Integration & API</h4>
            
            <div className="p-4 rounded-xl bg-slate-950 border border-slate-800 space-y-3">
              <div className="flex items-center justify-between">
                <span className="text-slate-400">API Endpoint:</span>
                <span className="font-mono text-indigo-300">http://localhost:8080/api/v1</span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-slate-400">Security Mode:</span>
                <span className="text-emerald-400 font-semibold flex items-center space-x-1">
                  <ShieldCheck className="w-3.5 h-3.5 inline mr-1" />
                  JWT Bearer Token
                </span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-slate-400">Database Engine:</span>
                <span className="font-mono text-slate-200">H2 / PostgreSQL</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
