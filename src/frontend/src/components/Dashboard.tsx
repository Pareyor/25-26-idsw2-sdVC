import React, { useEffect, useState } from 'react';
import { getMenuOptions } from '../services/menu.service';
import { logout } from '../services/auth.service';
import * as Icons from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

interface MenuOption {
  title: string;
  path: string;
  icon: string;
}

const Dashboard: React.FC = () => {
  const [options, setOptions] = useState<MenuOption[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    getMenuOptions()
      .then(data => {
        setOptions(Array.isArray(data) ? data : []);
        setLoading(false);
      })
      .catch(() => {
        navigate('/login');
      });
  }, [navigate]);

  const handleAction = async (path: string) => {
    if (path === '/logout') {
      if (window.confirm('¿Está seguro de que desea salir?')) {
        await logout();
        navigate('/login');
      }
    } else {
      navigate(path);
    }
  };

  const DynamicIcon = ({ name, size = 32 }: { name: string; size?: number }) => {
    if (!name) return <Icons.HelpCircle size={size} />;
    
    const iconName = name.split('-').map(s => s.charAt(0).toUpperCase() + s.slice(1)).join('') as keyof typeof Icons;
    const LucideIcon = (Icons[iconName] || Icons.HelpCircle || Icons.Info) as React.ElementType;
    
    return LucideIcon ? <LucideIcon size={size} /> : <div style={{ width: size, height: size }} />;
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-pulse text-2xl font-bold text-primary">Cargando experiencia...</div>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div className="header-titles">
          <h1>Panel de Control</h1>
          <p>Sistema de Gestión de Exámenes</p>
        </div>
      </header>

      <main className="menu-grid">
        {options.map((option, index) => (
          <button
            key={index}
            onClick={() => handleAction(option.path)}
            className="menu-item"
          >
            <div className="icon-wrapper">
              <DynamicIcon name={option.icon} />
            </div>
            <span>{option.title}</span>
          </button>
        ))}
      </main>
    </div>
  );
};

export default Dashboard;
