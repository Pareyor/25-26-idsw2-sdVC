import React, { useEffect, useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import * as Icons from 'lucide-react';
import { getMenuOptions } from '../services/menu.service';
import { logout, getCurrentUser } from '../services/auth.service';
import './Layout.css';

interface LayoutProps {
  children: React.ReactNode;
}

interface MenuOption {
  title: string;
  path: string;
  icon: string;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const [menuOptions, setMenuOptions] = useState<MenuOption[]>([]);
  const user = getCurrentUser();
  const navigate = useNavigate();

  useEffect(() => {
    getMenuOptions().then(setMenuOptions).catch(console.error);
  }, []);

  const handleLogout = async () => {
    if (window.confirm('¿Está seguro de que desea salir?')) {
      await logout();
      navigate('/login');
    }
  };

  const DynamicIcon = ({ name }: { name: string }) => {
    const LucideIcon = (Icons[name as keyof typeof Icons] || Icons.HelpCircle) as React.ElementType;
    return <LucideIcon size={20} />;
  };

  return (
    <div className="layout-wrapper">
      <aside className="sidebar">
        <div className="sidebar-brand">JORGESTOR</div>
        
        {user && (
          <div className="user-profile">
            <div className="user-avatar">
              <Icons.User size={24} />
            </div>
            <div className="user-info">
              <span className="user-label">Docente</span>
              <span className="user-name">{user.username}</span>
            </div>
          </div>
        )}

        <nav className="nav-menu">
          {menuOptions.map((opt) => (
            opt.path === '/logout' ? (
              <button key={opt.path} onClick={handleLogout} className="nav-item" style={{background: 'transparent', border: 'none', width: '100%', cursor: 'pointer', textAlign: 'left'}}>
                <DynamicIcon name={opt.icon} />
                {opt.title}
              </button>
            ) : (
              <NavLink key={opt.path} to={opt.path} className={({isActive}) => `nav-item ${isActive ? 'active' : ''}`}>
                <DynamicIcon name={opt.icon} />
                {opt.title}
              </NavLink>
            )
          ))}
        </nav>
      </aside>
      <main className="main-content">
        <div className="content-wrapper">
          {children}
        </div>
      </main>
    </div>
  );
};

export default Layout;
