import React, { useEffect, useState, useRef } from 'react';
import { getMenuOptions } from '../services/menu.service';
import { logout } from '../services/auth.service';
import { exportarConfiguracion, importarConfiguracion } from '../services/config.service';
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
  const fileInputRef = useRef<HTMLInputElement>(null);

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

  const handleExportar = async () => {
    try {
      const response = await exportarConfiguracion();
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const a = document.createElement('a');
      a.href = url;
      a.download = 'configuracion.json';
      a.click();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      alert('Error al exportar la configuración');
    }
  };

  const handleImportar = async (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      try {
        await importarConfiguracion(event.target.files[0]);
        alert('Configuración importada exitosamente');
        window.location.reload();
      } catch (error) {
        alert('Error al importar la configuración');
      }
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
        <div className="config-actions" style={{ display: 'flex', gap: '1rem', marginTop: '1rem', justifyContent: 'center' }}>
            <button onClick={handleExportar} className="btn-secondary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', padding: '0.75rem 1.5rem', borderRadius: '1rem', border: '1px solid var(--glass-border)', background: 'var(--card-bg)', cursor: 'pointer' }}>
                <Icons.Download size={18} /> Exportar
            </button>
            <button onClick={() => fileInputRef.current?.click()} className="btn-secondary" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', padding: '0.75rem 1.5rem', borderRadius: '1rem', border: '1px solid var(--glass-border)', background: 'var(--card-bg)', cursor: 'pointer' }}>
                <Icons.Upload size={18} /> Importar
            </button>
            <input type="file" ref={fileInputRef} onChange={handleImportar} style={{ display: 'none' }} accept=".json" />
        </div>
      </header>

      <main className="menu-grid">
        {options
          .filter(option => option.path !== '/logout')
          .map((option, index) => (
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

      <div style={{ marginTop: '2rem', display: 'flex', justifyContent: 'center', width: '100%' }}>
        {options
          .filter(option => option.path === '/logout')
          .map((option, index) => (
            <button
              key={index}
              onClick={() => handleAction(option.path)}
              className="menu-item"
              style={{ width: '250px' }}
            >
              <div className="icon-wrapper">
                <DynamicIcon name={option.icon} />
              </div>
              <span>{option.title}</span>
            </button>
          ))}
      </div>
    </div>
  );
};

export default Dashboard;
