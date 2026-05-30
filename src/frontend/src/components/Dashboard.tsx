import React, { useEffect, useState } from 'react';
import { getMenuOptions } from '../services/menu.service';
import { logout } from '../services/auth.service';
import * as Icons from 'lucide-react';
import { useNavigate } from 'react-router-dom';

interface MenuOption {
  title: string;
  path: string;
  icon: string;
}

const Dashboard: React.FC = () => {
  const [options, setOptions] = useState<MenuOption[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    getMenuOptions().then(setOptions).catch(() => navigate('/login'));
  }, [navigate]);

  const handleAction = (path: string) => {
    if (path === '/logout') {
      if (window.confirm('¿Está seguro de que desea salir?')) {
        logout();
        navigate('/login');
      }
    } else {
      console.log('Navegando a:', path);
      // Aquí navegaremos a los futuros componentes
    }
  };

  const DynamicIcon = ({ name, size = 24 }: { name: string; size?: number }) => {
    // Mapeo simple de nombres a componentes Lucide
    const iconName = name.split('-').map(s => s.charAt(0).toUpperCase() + s.slice(1)).join('') as keyof typeof Icons;
    const LucideIcon = (Icons[iconName] || Icons.HelpCircle) as React.ElementType;
    return <LucideIcon size={size} />;
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <header className="max-w-6xl mx-auto mb-12 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Jorgestor</h1>
          <p className="text-gray-600">Sistema de Gestión de Exámenes</p>
        </div>
        <div className="text-right">
            <span className="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full uppercase">
                Panel de Control
            </span>
        </div>
      </header>

      <main className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {options.map((option, index) => (
          <button
            key={index}
            onClick={() => handleAction(option.path)}
            className="flex flex-col items-center justify-center p-8 bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow-md hover:border-blue-200 transition-all duration-200 group"
          >
            <div className="p-4 rounded-full bg-blue-50 text-blue-600 group-hover:bg-blue-600 group-hover:text-white transition-colors duration-200 mb-4">
              <DynamicIcon name={option.icon} size={32} />
            </div>
            <span className="text-lg font-semibold text-gray-800">{option.title}</span>
          </button>
        ))}
      </main>
    </div>
  );
};

export default Dashboard;
