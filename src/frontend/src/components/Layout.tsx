import React, { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';
import * as Icons from 'lucide-react';
import { getMenuOptions } from '../services/menu.service';
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

  useEffect(() => {
    getMenuOptions().then(setMenuOptions).catch(console.error);
  }, []);

  const DynamicIcon = ({ name }: { name: string }) => {
    const LucideIcon = (Icons[name as keyof typeof Icons] || Icons.HelpCircle) as React.ElementType;
    return <LucideIcon size={20} />;
  };

  return (
    <div className="layout-wrapper">
      <aside className="sidebar">
        <div className="sidebar-brand">JORGESTOR</div>
        <nav className="nav-menu">
          {menuOptions.map((opt) => (
            <NavLink key={opt.path} to={opt.path} className={({isActive}) => `nav-item ${isActive ? 'active' : ''}`}>
              <DynamicIcon name={opt.icon} />
              {opt.title}
            </NavLink>
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
