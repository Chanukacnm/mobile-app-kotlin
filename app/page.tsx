import { Bell, User, Search, MapPin, Star, Clock, MessageCircle, Calendar, Home, Users } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"

export default function ServiceBookingApp() {
  const popularServices = [
    { name: "Plumbing", icon: "🔧", color: "bg-blue-100 text-blue-600" },
    { name: "Electrical", icon: "⚡", color: "bg-orange-100 text-orange-600" },
    { name: "Cleaning", icon: "🏠", color: "bg-green-100 text-green-600" },
    { name: "Auto Repair", icon: "🚗", color: "bg-red-100 text-red-600" },
    { name: "Tutoring", icon: "🎓", color: "bg-purple-100 text-purple-600" },
    { name: "Beauty", icon: "✂️", color: "bg-pink-100 text-pink-600" },
  ]

  const bookings = [
    {
      service: "Plumbing repair",
      provider: "Mike's Plumbing",
      time: "Today, 2:00 PM",
      status: "confirmed",
      avatar: "M",
    },
    {
      service: "Math Tutoring",
      provider: "Lisa Chen",
      time: "Tomorrow, 4:00 PM",
      status: "pending",
      avatar: "L",
    },
  ]

  const nearbyProviders = [
    {
      name: "Mike's Plumbing",
      service: "Plumbing",
      rating: 4.8,
      reviews: 127,
      distance: "0.3km",
      status: "Available",
      avatar: "M",
    },
    {
      name: "Sarah Electronics",
      service: "Electrical Work",
      rating: 4.4,
      reviews: 89,
      distance: "1.2km",
      status: "Available",
      avatar: "S",
    },
  ]

  return (
    <div className="max-w-sm mx-auto bg-white min-h-screen">
      {/* Status Bar */}
      <div className="flex justify-between items-center px-4 py-2 text-sm font-medium">
        <span>9:41</span>
        <div className="flex items-center gap-1">
          <div className="flex gap-1">
            <div className="w-1 h-1 bg-black rounded-full"></div>
            <div className="w-1 h-1 bg-black rounded-full"></div>
            <div className="w-1 h-1 bg-black rounded-full"></div>
            <div className="w-1 h-1 bg-gray-300 rounded-full"></div>
          </div>
          <div className="w-6 h-3 border border-black rounded-sm">
            <div className="w-4 h-2 bg-black rounded-sm m-0.5"></div>
          </div>
        </div>
      </div>

      {/* Header */}
      <div className="px-4 py-4">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Good Morning!</h1>
            <div className="flex items-center text-gray-500 text-sm mt-1">
              <MapPin className="w-4 h-4 mr-1" />
              Wellgama, Southern Province
            </div>
          </div>
          <div className="flex gap-2">
            <Button variant="ghost" size="icon" className="rounded-full">
              <Bell className="w-5 h-5" />
            </Button>
            <Button variant="ghost" size="icon" className="rounded-full">
              <User className="w-5 h-5" />
            </Button>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex gap-3 mb-6">
          <Button className="flex-1 bg-yellow-400 hover:bg-yellow-500 text-black font-medium">Find Services</Button>
          <Button variant="outline" className="flex-1 font-medium">
            <Users className="w-4 h-4 mr-2" />
            My Business
          </Button>
        </div>

        {/* Search Bar */}
        <div className="relative mb-6">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
          <Input placeholder="Search services..." className="pl-10 bg-gray-50 border-0 rounded-xl" />
        </div>
      </div>

      {/* Popular Services */}
      <div className="px-4 mb-6">
        <h2 className="text-lg font-semibold mb-4">Popular Services</h2>
        <div className="grid grid-cols-3 gap-4">
          {popularServices.map((service, index) => (
            <Card key={index} className="border-0 shadow-sm">
              <CardContent className="p-4 text-center">
                <div
                  className={`w-12 h-12 rounded-xl ${service.color} flex items-center justify-center mx-auto mb-2 text-xl`}
                >
                  {service.icon}
                </div>
                <p className="text-sm font-medium text-gray-700">{service.name}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      {/* Your Bookings */}
      <div className="px-4 mb-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">Your Bookings</h2>
          <Button variant="ghost" className="text-yellow-600 font-medium p-0">
            View All
          </Button>
        </div>
        <div className="space-y-3">
          {bookings.map((booking, index) => (
            <Card key={index} className="border-0 shadow-sm">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div className="flex-1">
                    <h3 className="font-medium text-gray-900">{booking.service}</h3>
                    <p className="text-sm text-gray-500">{booking.provider}</p>
                    <div className="flex items-center text-sm text-gray-500 mt-1">
                      <Clock className="w-4 h-4 mr-1" />
                      {booking.time}
                    </div>
                  </div>
                  <div className="flex items-center gap-3">
                    <Badge
                      variant={booking.status === "confirmed" ? "default" : "secondary"}
                      className={
                        booking.status === "confirmed" ? "bg-green-100 text-green-700" : "bg-yellow-100 text-yellow-700"
                      }
                    >
                      {booking.status}
                    </Badge>
                    <Avatar className="w-8 h-8 bg-yellow-400 text-black">
                      <AvatarFallback className="bg-yellow-400 text-black font-medium">{booking.avatar}</AvatarFallback>
                    </Avatar>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      {/* Nearby Providers */}
      <div className="px-4 mb-20">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">Nearby Providers</h2>
          <Button variant="ghost" className="text-yellow-600 font-medium p-0">
            See All
          </Button>
        </div>
        <div className="space-y-3">
          {nearbyProviders.map((provider, index) => (
            <Card key={index} className="border-0 shadow-sm">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <Avatar className="w-12 h-12 bg-gray-200">
                      <AvatarFallback className="bg-gray-200 text-gray-600 font-medium">
                        {provider.avatar}
                      </AvatarFallback>
                    </Avatar>
                    <div>
                      <h3 className="font-medium text-gray-900">{provider.name}</h3>
                      <p className="text-sm text-gray-500">{provider.service}</p>
                      <div className="flex items-center gap-4 mt-1">
                        <div className="flex items-center text-sm">
                          <Star className="w-4 h-4 text-yellow-400 fill-current mr-1" />
                          <span className="font-medium">{provider.rating}</span>
                          <span className="text-gray-500 ml-1">({provider.reviews})</span>
                        </div>
                        <div className="flex items-center text-sm text-gray-500">
                          <MapPin className="w-4 h-4 mr-1" />
                          {provider.distance}
                        </div>
                        <Badge className="bg-green-100 text-green-700 text-xs">{provider.status}</Badge>
                      </div>
                    </div>
                  </div>
                  <Button variant="ghost" size="icon">
                    <span className="text-gray-400">›</span>
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-1/2 transform -translate-x-1/2 w-full max-w-sm bg-white border-t border-gray-200">
        <div className="flex justify-around py-2">
          <Button variant="ghost" className="flex-col gap-1 h-auto py-2 text-yellow-600">
            <Home className="w-5 h-5" />
            <span className="text-xs">Home</span>
          </Button>
          <Button variant="ghost" className="flex-col gap-1 h-auto py-2 text-gray-500">
            <Search className="w-5 h-5" />
            <span className="text-xs">Search</span>
          </Button>
          <Button variant="ghost" className="flex-col gap-1 h-auto py-2 text-gray-500">
            <Calendar className="w-5 h-5" />
            <span className="text-xs">Bookings</span>
          </Button>
          <Button variant="ghost" className="flex-col gap-1 h-auto py-2 text-gray-500">
            <MessageCircle className="w-5 h-5" />
            <span className="text-xs">Messages</span>
          </Button>
          <Button variant="ghost" className="flex-col gap-1 h-auto py-2 text-gray-500">
            <User className="w-5 h-5" />
            <span className="text-xs">Profile</span>
          </Button>
        </div>
      </div>
    </div>
  )
}
